package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeachesServiceImpl implements TeachesService {

    @Autowired
    private TeachesDao teachesDao;

    @Transactional
    @Override
    public Optional<Teaches> addSubjectToUser(int userid, int subjectid, int price, int level) {
        return Optional.of(teachesDao.addSubjectToUser(userid, subjectid, price, level));
    }

    @Override
    public Optional<List<Teaches>> getSubjectListByUser(int userid) {
        return teachesDao.findSubjectByUser(userid);
    }

    @Transactional
    @Override
    public int removeSubjectToUser(int userid, int subjectid) {
        return teachesDao.removeSubjectToUser(userid, subjectid);
    }

    @Override
    public Optional<List<SubjectInfo>> getSubjectInfoListByUser(int userid) {
        return teachesDao.getSubjectInfoListByUser(userid);
    }

    @Override
    public Optional<Teaches> findByUserAndSubject(int userId, int subjectId) {
        return teachesDao.findByUserAndSubject(userId, subjectId);
    }
}
