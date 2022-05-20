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
    public Page<TeacherInfo> filterUsers(String searchedSubject, Integer order, Integer price, Integer level, Integer rating, Integer page, Integer pageSize) {
        int maxLevel = level, minLevel = level;
        if (level <= 0 || level > MAX_LEVEL){
            maxLevel = MAX_LEVEL;
            minLevel = ANY_LEVEL;
        }
        Integer maxPrice = teachesDao.getMostExpensiveUserFee(searchedSubject);
        if (price > maxPrice) price = maxPrice;
        return teachesDao.filterUsers(searchedSubject, price, minLevel, maxLevel, rating, order, new PageRequest(page, pageSize));
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
            if (newLevels.isEmpty()) {
                availableSubjects.remove(subject);
            } else {
                availableSubjects.replace(subject, newLevels);
            }
        });
        return availableSubjects;
    }

    @Transactional
    @Override
    public Integer getMostExpensiveUserFee(String searchedSubject) {
        return teachesDao.getMostExpensiveUserFee(searchedSubject);
    }

    @Override
    public List<Teaches> get(Long teacherId) {
        return teachesDao.get(teacherId);
    }
}
