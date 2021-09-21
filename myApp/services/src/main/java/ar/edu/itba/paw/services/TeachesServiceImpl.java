package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TeachesDao;
import ar.edu.itba.paw.interfaces.TeachesService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Teaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachesServiceImpl implements TeachesService {

    @Autowired
    TeachesDao teachesDao;

    @Override
    public Teaches addSubjectToUser(int userid, int subjectid, int price, int level) {
        return teachesDao.addSubjectToUser(userid, subjectid, price, level);
    }

    @Override
    public List<Teaches> getSubjectListByUser(int userid) {
        return teachesDao.findSubjectByUser(userid);
    }
}
