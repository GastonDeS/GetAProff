package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.interfaces.services.ClassService;
import ar.edu.itba.paw.models.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassDao classDao;

    @Override
    public Optional<Class> findById(Long id) {
        return classDao.get(id);
    }

    @Override
    public List<Class> findClassesByStudentAndStatus(Long studentId, Integer status) {
        if (status == 3) {
            return classDao.findClassesByStudentId(studentId);
        }
        else if (status == 2) {
            return classDao.findClassesByStudentAndMultipleStatus(studentId, status);
        }
        return classDao.findClassesByStudentAndStatus(studentId, status);
    }

    @Override
    public List<Class> findClassesByTeacherAndStatus(Long teacherId, Integer status) {
        if (status == 3) {
            return classDao.findClassesByTeacherId(teacherId);
        }
        else if (status == 2) {
            return classDao.findClassesByTeacherAndMultipleStatus(teacherId, status);
        }
        return classDao.findClassesByTeacherAndStatus(teacherId, status);
    }

    @Transactional
    @Override
    public Class create(Long studentId, Long teacherId, int level, Long subjectId, int price) {
        return classDao.create(studentId, teacherId, level, subjectId, price);
    }

    @Transactional
    @Override
    public int setStatus(Long classId, int status) {
        return classDao.setStatus(classId, status);
    }

}
