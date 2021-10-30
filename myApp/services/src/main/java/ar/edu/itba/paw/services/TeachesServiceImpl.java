package ar.edu.itba.paw.services;

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

    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0,MAX_LEVEL = 3, GET_ALL = 0, PAGE_SIZE = 9;

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

    @Transactional
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

    @Transactional
    @Override
    public Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level) {
        return teachesDao.findByUserAndSubjectAndLevel(userId, subjectId, level);
    }

    @Transactional
    @Override
    public List<CardProfile> findTeachersTeachingSubject(String searchedSubject, String offset){
        List<Teaches> teachersTeachingSubject = teachesDao.findTeachersTeachingSubject(searchedSubject);
        return removeDuplicatedTeachers(teachersTeachingSubject, ANY_RATING.floatValue(), Integer.parseInt(offset), RAND_ORDER);
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
        List<Teaches> teachersTeachingSubject = teachesDao.filterUsers(searchedSubject, intPrice, minLevel, maxLevel);
        return removeDuplicatedTeachers(teachersTeachingSubject, Float.parseFloat(rating), Integer.parseInt(offset), Integer.parseInt(order));
    }

    private List<CardProfile> removeDuplicatedTeachers(List<Teaches> teachersTeachingSubject, Float rating, Integer offset, Integer order) {
        Map<Long, CardProfile> teachersResultMap = new HashMap<>();
        for (Teaches teachingInfo : teachersTeachingSubject) {
            User teacher = teachingInfo.getTeacher();
            Float teacherRating = ratingService.getRatingById(teacher.getId()).getValue1();
            if (teacherRating.compareTo(rating) >= 0 || rating.equals(ANY_RATING.floatValue())) {
                teachersResultMap.putIfAbsent(teacher.getId(),
                        new CardProfile(teacher.getId(), teacher.getName(), getMaxPrice(teacher.getId()), getMinPrice(teacher.getId()), teacher.getDescription(),
                                imageService.hasImage(teacher.getId()), teacherRating));
            }
        }
        List<CardProfile> resultList = new ArrayList<>(teachersResultMap.values());
        if (order != 0) resultList.sort(cardProfileComparator(order));
        if (offset == 0) return resultList;
        if ((offset * PAGE_SIZE) > resultList.size())
            return resultList.subList((offset - 1) * PAGE_SIZE, resultList.size());
        return resultList.subList((offset - 1) * PAGE_SIZE, offset * PAGE_SIZE);
    }

    private Comparator<CardProfile> cardProfileComparator(Integer order) {
        Comparator<CardProfile> priceComparator = Comparator.comparingInt(CardProfile::getMaxPrice);
        Comparator<CardProfile> rateComparator = (o1, o2) -> Float.compare(o1.getRate(), o2.getRate());
        switch (order) {
            case 1:
                return priceComparator;
            case 2:
                return priceComparator.reversed();
            case 3:
                return rateComparator;
            case 4:
                return rateComparator.reversed();
            default:
                return null;
        }
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
    public Integer getMostExpensiveUserFee(String searchedSubject) {
        return teachesDao.getMostExpensiveUserFee(searchedSubject);
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
