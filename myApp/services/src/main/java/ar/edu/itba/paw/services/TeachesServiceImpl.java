package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.RatingService;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TeachesServiceImpl implements TeachesService {

    @Autowired
    private TeachesDao teachesDao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RatingService ratingService;

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
    public List<SubjectInfo> getSubjectInfoListByUser(Long teacherId) {
        List<Teaches> teachesList = teachesDao.get(teacherId);
        List<SubjectInfo> subjectInfoList = new ArrayList<>();
        for(Teaches teaches : teachesList) {
            Subject subject = teaches.getSubject();
            SubjectInfo subjectInfo = new SubjectInfo(subject.getId(), subject.getName(), teaches.getPrice(), teaches.getLevel());
            subjectInfoList.add(subjectInfo);
        }
        return subjectInfoList;
    }

    @Override
    public Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level) {
        return teachesDao.findByUserAndSubjectAndLevel(userId, subjectId, level);
    }

    @Override
    public List<CardProfile> filterTeachingUsers(String subject){
        List<Teaches> teachersTeachingSubject = teachesDao.filterTeachersTeachingSubject(subject);
        List<CardProfile> result = new ArrayList<>();
        for (Teaches teachingInfo : teachersTeachingSubject) {
            User teacher = teachingInfo.getTeacher();
            result.add(new CardProfile(teacher.getId(),teacher.getName(), getMaxPrice(teacher.getId()), getMinPrice(teacher.getId()), teacher.getDescription(),
                    imageService.hasImage(teacher.getId()), ratingService.getRatingById(teacher.getId()).getValue1()));
        }
        return result;
    }

    @Override
    public int getMaxPrice(Long teacherId) {
        return teachesDao.getMaxPrice(teacherId);
    }

    @Override
    public int getMinPrice(Long teacherId) {
        return teachesDao.getMinPrice(teacherId);
    }

    @Override
    public List<Teaches> get(Long teacherId) {
        return teachesDao.get(teacherId);
    }

}
