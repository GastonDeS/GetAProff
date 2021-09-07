package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    @Override
    public Optional<Subject> findById(int id) {
        return subjectDao.findById(id);
    }

    @Override
    public Subject create(String subject) {
        return subjectDao.create(subject);
    }

    @Override
    public List<Subject> list() {
        return subjectDao.listSubjects();
    }
}
