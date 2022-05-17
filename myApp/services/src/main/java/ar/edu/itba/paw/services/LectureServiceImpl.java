package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
import ar.edu.itba.paw.interfaces.daos.SubjectFileDao;
import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LectureServiceImpl implements LectureService {

    public static final int ANY_STATUS = -1;

    @Autowired
    private LectureDao lectureDao;

    @Autowired
    private SubjectFileDao subjectFileDao;

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureDao.get(id);
    }

    @Override
    public Page<Lecture> findClasses(Long uid, boolean asTeacher, Integer status, Integer page, Integer pageSize) {
        if (asTeacher)
            return findClassesByTeacherAndStatus(uid, status, new PageRequest(page, pageSize));
        else
            return findClassesByStudentAndStatus(uid, status, new PageRequest(page, pageSize));
    }

    private Page<Lecture> findClassesByStudentAndStatus(Long studentId, Integer status, PageRequest pageRequest) {
        Page<Lecture> lecturepage = null;
        if (status == ANY_STATUS) {
            lecturepage = lectureDao.findClassesByStudentId(studentId, pageRequest);
        }
        else {
            lecturepage = lectureDao.findClassesByStudentAndStatus(studentId, status, pageRequest);
        }
        lecturepage.getContent().forEach((e) -> e.setNotifications(lectureDao.getNotificationsCount(e.getClassId(),1)));
        return lecturepage;
    }

    private Page<Lecture> findClassesByTeacherAndStatus(Long teacherId, Integer status, PageRequest pageRequest) {
        Page<Lecture> lecturepage = null;
        if (status == ANY_STATUS) {
            lecturepage = lectureDao.findClassesByTeacherId(teacherId, pageRequest);
        }
        else {
            lecturepage = lectureDao.findClassesByTeacherAndStatus(teacherId, status, pageRequest);
        }
        lecturepage.getContent().forEach((e) -> e.setNotifications(lectureDao.getNotificationsCount(e.getClassId(),0)));
        return lecturepage;
    }

    @Transactional
    @Override
    public Optional<Lecture> create(Long studentId, Long teacherId, int level, Long subjectId, int price) {
        return Optional.ofNullable(lectureDao.create(studentId, teacherId, level, subjectId, price));
    }

    @Transactional
    @Override
    public int setStatus(Long classId, int status) {
        return lectureDao.setStatus(classId, status);
    }

    @Transactional
    @Override
    public int refreshTime(Long classId, int role) {
        return lectureDao.refreshTime(classId, role);
    }

    @Transactional
    @Override
    public int changeFileVisibility(Long subjectFileId, Long lectureId) {
        if (lectureDao.getSharedFilesByTeacher(lectureId).stream().map(e -> e.getFileId().equals(subjectFileId)).count() == 1) {
            return lectureDao.stopSharingFileInLecture(subjectFileId,lectureId);
        }
        return lectureDao.addSharedFileToLecture(subjectFileId, lectureId);
    }

    @Transactional
    @Override
    public int stopSharingFileInLecture(Long subjectFileId, Long lecture){
        return lectureDao.stopSharingFileInLecture(subjectFileId, lecture);
    }

    @Transactional
    @Override
    public int shareFileInLecture(Long subjectFileId, Long lecture){
        return lectureDao.stopSharingFileInLecture(subjectFileId, lecture);
    }

    @Override
    public Pair<List<SubjectFile>, List<SubjectFile>> getTeacherFiles(Long lectureId, Long userId) {
        Pair<List<SubjectFile>, List<SubjectFile>> lectureFiles = new Pair<>(null, null);
        Optional<Lecture> lecture = lectureDao.get(lectureId);
        if (!lecture.isPresent())
            throw new NoSuchElementException();
        if (lecture.get().getTeacher().getId().equals(userId)) {
            lectureFiles.setValue2(this.getFilesNotSharedInLecture(lectureId, userId));
        }
        lectureFiles.setValue1(this.getFilesSharedInLecture(lectureId));
        return lectureFiles;
    }


    @Transactional
    @Override
    public List<SubjectFile> getFilesNotSharedInLecture(Long lectureId, Long teacherId){
        List<SubjectFile> nonSharedFiles= new ArrayList<>();
        List<SubjectFile> allFilesForLecture = subjectFileDao.getAllSubjectFilesFromUser(teacherId);
        List<SubjectFile> sharedFiles  = lectureDao.getSharedFilesByTeacher(lectureId);
        for(SubjectFile file : allFilesForLecture){
            if(!sharedFiles.contains(file))
                nonSharedFiles.add(file);
        }
        return nonSharedFiles;
    }

    @Transactional
    @Override
    public List<SubjectFile> getFilesSharedInLecture(Long classId) {
        return lectureDao.getSharedFilesByTeacher(classId);
    }
}
