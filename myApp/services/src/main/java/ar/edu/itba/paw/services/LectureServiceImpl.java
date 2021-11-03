package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.models.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureDao lectureDao;

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureDao.get(id);
    }

    @Override
    public List<Lecture> findClassesByStudentAndStatus(Long studentId, Integer status) {
        if (status == 3) {
            return lectureDao.findClassesByStudentId(studentId);
        }
        else if (status == 2) {
            return lectureDao.findClassesByStudentAndMultipleStatus(studentId, status);
        }
        return lectureDao.findClassesByStudentAndStatus(studentId, status);
    }

    @Override
    public List<Lecture> findClassesByTeacherAndStatus(Long teacherId, Integer status) {
        if (status == 3) {
            return lectureDao.findClassesByTeacherId(teacherId);
        }
        else if (status == 2) {
            return lectureDao.findClassesByTeacherAndMultipleStatus(teacherId, status);
        }
        return lectureDao.findClassesByTeacherAndStatus(teacherId, status);
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

}
