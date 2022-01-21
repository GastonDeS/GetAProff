package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TeachesServiceImpl implements TeachesService {

    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0, MAX_LEVEL = 3, GET_ALL = 0, PAGE_SIZE = 9;

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

    @Transactional
    @Override
    public List<SubjectInfo> getSubjectInfoListByUser(Long teacherId) {
        List<SubjectInfo> subjectInfoList = new ArrayList<>();
        List<Object> subjectInfoListRaw = teachesDao.getSubjectInfoListByUser(teacherId);
        subjectInfoListRaw.forEach((subjectInfoRaw) -> {
            Object[] subjectInfo = (Object[]) subjectInfoRaw;
            subjectInfoList.add(new SubjectInfo(((Number) subjectInfo[0]).longValue(), subjectInfo[1].toString(),
                    ((Number) subjectInfo[2]).intValue(), ((Number) subjectInfo[3]).intValue()));
        });
        return subjectInfoList;
    }

    @Override
    public List<Subject> getListOfAllSubjectsTeachedByUser(Long userId) {
        return teachesDao.getListOfAllSubjectsTeachedByUser(userId);
    }

    @Transactional
    @Override
    public Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level) {
        return teachesDao.findByUserAndSubjectAndLevel(userId, subjectId, level);
    }

    @Transactional
    @Override
    public List<CardProfile> findTeachersTeachingSubject(String searchedSubject, String offset) {
        List<Object> teachersTeachingSubjectRaw = teachesDao.filterUsers(searchedSubject,
                teachesDao.getMostExpensiveUserFee(searchedSubject), ANY_LEVEL, MAX_LEVEL, ANY_RATING, RAND_ORDER, Integer.parseInt(offset));
        return cardProfileConverter(teachersTeachingSubjectRaw);
    }

    @Transactional
    @Override
    public List<CardProfile> filterUsers(String searchedSubject, String order, String price, String level, String rating, String offset) {
        int lvl = Integer.parseInt(level);
        int maxLevel = lvl, minLevel = lvl;
        if (lvl <= 0 || lvl > MAX_LEVEL){
            maxLevel = MAX_LEVEL;
            minLevel = ANY_LEVEL;
        }
        Integer maxPrice = teachesDao.getMostExpensiveUserFee(searchedSubject);
        int intPrice = Integer.parseInt(price);
        if (intPrice > maxPrice) intPrice = maxPrice;
        List<Object> teachersTeachingSubjectRaw = teachesDao.filterUsers(searchedSubject, intPrice, minLevel, maxLevel,
                Integer.parseInt(rating), Integer.parseInt(order), Integer.parseInt(offset));
        return cardProfileConverter(teachersTeachingSubjectRaw);
    }

    private static List<CardProfile> cardProfileConverter(List<Object> teachersTeachingSubjectRaw) {
        List<CardProfile> teachersTeachingSubject = new ArrayList<>();
        teachersTeachingSubjectRaw.forEach((teaches) -> {
            Object[] cardProfileInfo = (Object[]) teaches;
            teachersTeachingSubject.add(
                    new CardProfile(((Number) cardProfileInfo[0]).longValue(), cardProfileInfo[1].toString(), ((Number) cardProfileInfo[2]).intValue(),
                            ((Number) cardProfileInfo[3]).intValue(), cardProfileInfo[4].toString(), ((Number) cardProfileInfo[5]).floatValue())
            );
        });
        return teachersTeachingSubject;
    }

    @Override
    public Integer getPageQty(String searchedSubject, String price, String level, String rating) {
        List<CardProfile> teachersListResult = filterUsers(searchedSubject, RAND_ORDER.toString(), price, level, rating, GET_ALL.toString());
        return (int) Math.ceil(teachersListResult.size()/(double) PAGE_SIZE);
    }

    @Override
    public Integer getPageQty(String searchedSubject) {
        return (int) Math.ceil(findTeachersTeachingSubject(searchedSubject, GET_ALL.toString()).size()/(double) PAGE_SIZE);
    }

    @Override
    public List<CardProfile> getTopRatedTeachers() {
        return teachesDao.getTopRatedTeachers();
    }

    @Override
    public List<CardProfile> getMostRequested() {
        return teachesDao.getMostRequested();
    }

    @Override
    public Integer getMostExpensiveUserFee(String searchedSubject) {
        return teachesDao.getMostExpensiveUserFee(searchedSubject);
    }

    @Override
    public List<Teaches> get(Long teacherId) {
        return teachesDao.get(teacherId);
    }

}
