package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
import ar.edu.itba.paw.interfaces.daos.SubjectFileDao;
import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.SubjectFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureDao lectureDao;

    @Autowired
    private SubjectFileDao subjectFileDao;

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureDao.get(id);
    }

    @Override
    public List<Lecture> findClassesByStudentAndStatus(Long studentId, Integer status) {
        List<Lecture> lectureList = new ArrayList<>();
        if (status == 3) {
            lectureList.addAll(lectureDao.findClassesByStudentId(studentId));
        }
        else if (status == 2) {
             lectureList.addAll(lectureDao.findClassesByStudentAndMultipleStatus(studentId, status));
        } else {
            lectureList.addAll(lectureDao.findClassesByStudentAndStatus(studentId, status));
        }
        lectureList.forEach((e) -> e.setNotifications(getNotificationsCount(e.getClassId(),1)));
        Collections.reverse(lectureList);
        return lectureList;
    }

    @Override
    public List<Lecture> findClassesByTeacherAndStatus(Long teacherId, Integer status) {
        List<Lecture> lectureList = new ArrayList<>();
        if (status == 3) {
            lectureList.addAll(lectureDao.findClassesByTeacherId(teacherId));
        }
        else if (status == 2) {
            lectureList.addAll(lectureDao.findClassesByTeacherAndMultipleStatus(teacherId, status));
        }else {
            lectureList.addAll(lectureDao.findClassesByTeacherAndStatus(teacherId, status));
        }
        lectureList.forEach((e) -> {e.setNotifications(getNotificationsCount(e.getClassId(),0));});
        Collections.reverse(lectureList);
        return lectureList;
    }

    @Transactional
    @Override
    public Lecture create(Long studentId, Long teacherId, int level, Long subjectId, int price) {
        return lectureDao.create(studentId, teacherId, level, subjectId, price);
    }

    @Transactional
    @Override
    public int setStatus(Long classId, int status) {
        return lectureDao.setStatus(classId, status);
    }

    @Override
    public Integer getNotificationsCount( Long classId, int role) {
        return lectureDao.getNotificationsCount( classId, role);
    }

    @Transactional
    @Override
    public int refreshTime(Long classId, int role) {
        return lectureDao.refreshTime(classId, role);
    }

    @Transactional
    @Override
    public int addSharedFileToLecture(Long subjectFileId, Long lectureId) {
        return lectureDao.addSharedFileToLecture(subjectFileId, lectureId);
    }

    @Transactional
    @Override
    public int stopSharingFileInLecture(Long subjectFileId, Long lectureId) {
        return lectureDao.stopSharingFileInLecture(subjectFileId,lectureId);
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
    public List<SubjectFile> getSharedFilesByTeacher(Long classId) {
        return lectureDao.getSharedFilesByTeacher(classId);
    }
}
