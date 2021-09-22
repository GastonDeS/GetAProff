package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TeachesDao;
import ar.edu.itba.paw.interfaces.TeachesService;
import ar.edu.itba.paw.models.Teaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeachesServiceImpl implements TeachesService {

    @Autowired
    TeachesDao teachesDao;

    @Override
    public Optional<Teaches> addSubjectToUser(int userid, int subjectid, int price, int level) {
        if (teachesDao.findUserSubject(userid, subjectid) != null) {
            return Optional.empty();
        }
        return Optional.of(teachesDao.addSubjectToUser(userid, subjectid, price, level));
    }

    @Override
    public List<Teaches> getSubjectListByUser(int userid) {
        return teachesDao.findSubjectByUser(userid);
    }

    @Override
    public int removeSubjectToUser(int userid, int subjectid) {
        return teachesDao.removeSubjectToUser(userid, subjectid);
    }
}
