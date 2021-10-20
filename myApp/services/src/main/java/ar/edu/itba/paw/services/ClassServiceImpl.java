package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.interfaces.daos.SubjectDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.ClassService;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassDao classDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubjectDao subjectDao;


    @Override
    public Optional<Class> findById(Long id) {
        return classDao.get(id);
    }

    @Override
    public List<Class> findClassesByStudentId(Long id) {
        User student = userDao.get(id).orElseThrow(RuntimeException::new);
        return classDao.findClassesByStudentId(student);
    }

    @Override
    public List<Class> findClassesByTeacherId(Long id) {
        User teacher = userDao.get(id).orElseThrow(RuntimeException::new);
        return classDao.findClassesByTeacherId(teacher);
    }

    @Transactional
    @Override
    public Class create(Long studentId, Long teacherId, int level, Long subjectId, int price, int status, String message) {
        if (!userDao.get(studentId).isPresent() || !userDao.get(teacherId).isPresent() || !subjectDao.findById(subjectId).isPresent()){
            throw new RuntimeException();
        }
        return classDao.create(studentId, teacherId, level, subjectId, price, status, message);
    }

    @Transactional
    @Override
    public int setStatus(Long classId, int status) {
        return classDao.setStatus(classId, status);
    }

    @Transactional
    @Override
    public int setDeleted(Long classId, int deleted) {
        return classDao.setDeleted(classId, deleted);
    }

    @Transactional
    @Override
    public int setRequest(Long classId, String message) {
        return classDao.setRequest(classId, message);
    }

    @Transactional
    @Override
    public int setReply(Long classId, String message) {
        return classDao.setReply(classId, message);
    }

}
