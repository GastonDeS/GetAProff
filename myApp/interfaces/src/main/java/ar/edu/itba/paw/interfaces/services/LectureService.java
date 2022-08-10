package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.utils.Pair;
import java.util.List;
import java.util.Optional;

public interface LectureService {
    Optional<Lecture> findById(Long id);

    Page<Lecture> findClasses(Long uid, boolean asTeacher, Integer status, Integer page, Integer pageSize);

    Optional<Lecture> create(Long studentId, Long teacherId, int level, Long subjectId, int price, String uri);

    int setStatus(Long classId, int status);

    int refreshTime(Long classId, int isStudent);

    int stopSharingFileInLecture(Long subjectFileId, Long lecture);

    int shareFileInLecture(Long subjectFileId, Long lecture);

    Pair<List<SubjectFile>, List<SubjectFile>> getTeacherFiles(Long lectureId, Long userId);

    List<SubjectFile> getFilesNotSharedInLecture(Long lectureId, Long teacherId);

    List<SubjectFile> getFilesSharedInLecture(Long classId);
}
