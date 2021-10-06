package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.interfaces.services.ClassService;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassDao classDao;

    @Override
    public Optional<Class> findById(int id) {
        return classDao.get(id);
    }

    @Override
    public List<ClassInfo> findClassesByStudentId(int id) {
        return classDao.findClassesByStudentId(id);
    }

    @Override
    public List<ClassInfo> findClassesByTeacherId(int id) {
        return classDao.findClassesByTeacherId(id);
    }

    @Override
    public Class create(int studentId, int teacherId, int level, int subjectId, int price, int status, String message) {
        return classDao.create(studentId, teacherId, level, subjectId, price, status, message);
    }

    @Override
    public int setStatus(int classId, int status) {
        return classDao.setStatus(classId, status);
    }

    @Override
    public int setDeleted(int classId, int deleted) {
        return classDao.setDeleted(classId, deleted);
    }

    @Override
    public int setRequest(int classId, String message) {
        return classDao.setRequest(classId, message);
    }

    @Override
    public int setReply(int classId, String message) {
        return classDao.setReply(classId, message);
    }

}
