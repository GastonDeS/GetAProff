package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
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

    @Autowired
    private SubjectDao subjectDao;

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

//    @Transactional
//    @Override
//    public List<Teaches> getSubjectInfoListByUser(Long teacherId) {
//        return teachesDao.getSubjectInfoListByUser(teacherId);
//    }

    @Override
    public List<Subject> getListOfAllSubjectsTaughtByUser(Long userId) {
        return teachesDao.getListOfAllSubjectsTeachedByUser(userId);
    }

    @Transactional
    @Override
    public Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level) {
        return teachesDao.findByUserAndSubjectAndLevel(userId, subjectId, level);
    }

    @Transactional
    @Override
    public List<TeacherInfo> findTeachersTeachingSubject(String searchedSubject, Integer offset) {
        return teachesDao.filterUsers(searchedSubject,
                teachesDao.getMostExpensiveUserFee(searchedSubject), ANY_LEVEL, MAX_LEVEL, ANY_RATING, RAND_ORDER, offset);
    }

    @Transactional
    @Override
    public List<TeacherInfo> filterUsers(String searchedSubject, Integer order, Integer price, Integer level, Integer rating, Integer offset) {
        int maxLevel = level, minLevel = level;
        if (level <= 0 || level > MAX_LEVEL){
            maxLevel = MAX_LEVEL;
            minLevel = ANY_LEVEL;
        }
        Integer maxPrice = teachesDao.getMostExpensiveUserFee(searchedSubject);
        if (price > maxPrice) price = maxPrice;
        return teachesDao.filterUsers(searchedSubject, price, minLevel, maxLevel, rating, order, offset);
    }

    @Override
    public Integer getPageQty(String searchedSubject, Integer price, Integer level, Integer rating) {
        List<TeacherInfo> teachersListResult = filterUsers(searchedSubject, RAND_ORDER, price, level, rating, GET_ALL);
        return (int) Math.ceil(teachersListResult.size()/(double) PAGE_SIZE);
    }

    @Override
    public Integer getPageQty(String searchedSubject) {
        return (int) Math.ceil(findTeachersTeachingSubject(searchedSubject, GET_ALL).size()/(double) PAGE_SIZE);
    }

    @Override
    public List<TeacherInfo> getTopRatedTeachers() {
        return teachesDao.getTopRatedTeachers();
    }

    @Override
    public List<TeacherInfo> getMostRequested() {
        return teachesDao.getMostRequested();
    }

    @Override
    public Map<Subject, List<Integer>> getSubjectAndLevelsTaughtByUser(Long userId) {
        Map<Subject, List<Integer>> subjectsAndLevels = new HashMap<>();
        teachesDao.get(userId).forEach(teaches -> {
            Subject subject = teaches.getSubject();
            subjectsAndLevels.putIfAbsent(subject, new ArrayList<>());
            subjectsAndLevels.get(subject).add(teaches.getLevel());
        });
        return subjectsAndLevels;
    }

    @Override
    public Optional<TeacherInfo> getTeacherInfo(Long teacherId) {
        return teachesDao.getTeacherInfo(teacherId);
    }

    @Override
    public Map<Subject, List<Integer>> getSubjectAndLevelsAvailableForUser(Long userId) {
        Map<Subject, List<Integer>> availableSubjects = new HashMap<>();
        subjectDao.listSubjects().forEach(subject -> {
            availableSubjects.put(subject, new ArrayList<>(Arrays.asList(0, 1, 2, 3)));
        });
        teachesDao.get(userId).forEach(teaches -> {
            Subject subject = teaches.getSubject();
            List<Integer> newLevels = availableSubjects.get(subject);
            newLevels.remove(new Integer(teaches.getLevel()));
            availableSubjects.replace(subject, newLevels);
        });
        return availableSubjects;
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
