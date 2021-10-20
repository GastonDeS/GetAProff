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
    public Optional<Teaches> addSubjectToUser(Long userId, Long subjectId, int price, int level) {
        return Optional.of(teachesDao.addSubjectToUser(userId, subjectId, price, level));
    }

    @Transactional
    @Override
    public int removeSubjectToUser(Long userId, Long subjectId, int level) {
        return teachesDao.removeSubjectToUser(userId, subjectId, level);
    }

    @Override
    public List<SubjectInfo> getSubjectInfoListByUser(Long userId) {
        return teachesDao.getSubjectInfoListByUser(userId);
    }

    @Override
    public Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level) {
        return teachesDao.findByUserAndSubjectAndLevel(userId, subjectId, level);
    }
}
