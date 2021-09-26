package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeachesServiceImpl implements TeachesService {

    @Autowired
    private TeachesDao teachesDao;

    @Autowired
    private SubjectService subjectService;

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

    @Override
    public List<SubjectInfo> getSubjectInfoListByUser(int userid) {
        List<Teaches> teachesList = teachesDao.findSubjectByUser(userid);
        List<SubjectInfo> subjectsGiven = new ArrayList<>();
        for(Teaches t : teachesList) {
            Optional<Subject> maybeSubj= subjectService.findById(t.getSubjectId());
            if (!maybeSubj.isPresent()) continue;
            String name = maybeSubj.get().getName();
            subjectsGiven.add(new SubjectInfo(t.getSubjectId(), name, t.getPrice(), t.getLevel()));
        }
        return subjectsGiven;
    }
}
