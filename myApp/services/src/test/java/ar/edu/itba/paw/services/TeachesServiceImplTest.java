package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeachesServiceImplTest {

    private static final String USERNAME = "John Doe";
    private static final String MAIL_ONE = "johndoe@mail.com";
    private static final String MAIL_TWO = "janedoe@mail.com";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final Long USER_ID_ONE = 1L;
    private static final Long USER_ID_TWO = 2L;
    private static final String SUBJECT_ONE = "MATE 1";
    private static final String SUBJECT_TWO = "MATE 2";
    private static final Long SUBJECT_ID_ONE = 1L;
    private static final Long SUBJECT_ID_TWO = 2L;
    private static final Integer PRICE = 500;
    private static final Integer LEVEL_ONE = 1;
    private static final Integer LEVEL_TWO = 2;
    private static final Integer LEVEL_THREE = 3;
    private static final Integer LEVEL_FOUR = 0;
    private static final Integer PAGE = 1;
    private static final Integer PAGE_SIZE = 9;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final float RATE = 3.99f ;
    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0, MAX_LEVEL = 3, GET_ALL = 0, OVER_PRICE = 1500;

    @InjectMocks
    private TeachesServiceImpl teachesService = new TeachesServiceImpl();

    @Mock
    private TeachesDao teachesMockDao;

    @Mock
    private SubjectDao subjectMockDao;

//    @Test
//    public void  testGetSubjectInfoListByUser() {
//        //1. Setup - precondiciones
//        final List<Object> subjectInfoRaw = new ArrayList<>();
//        subjectInfoRaw.add(new Object[] {SUBJECT_ID_ONE, SUBJECT_ONE, PRICE, LEVEL});
//        subjectInfoRaw.add(new Object[] {SUBJECT_ID_TWO, SUBJECT_TWO, PRICE, LEVEL});
//        when(mockDao.getSubjectInfoListByUser(eq(USER_ID_ONE))).thenReturn(subjectInfoRaw);
//
//        //2. Ejercito la class under test una unica linea
//        final List<SubjectInfo> maybeSubjectInfoList = teachesService.getSubjectInfoListByUser(USER_ID_ONE);
//
//        //3. Asserts - postcondiciones
//        final List<SubjectInfo> subjectInfoList = new ArrayList<>();
//        subjectInfoList.add(new SubjectInfo(SUBJECT_ID_ONE, SUBJECT_ONE, PRICE, LEVEL));
//        subjectInfoList.add(new SubjectInfo(SUBJECT_ID_TWO, SUBJECT_TWO, PRICE, LEVEL));
//        Assert.assertEquals(subjectInfoList, maybeSubjectInfoList);
//    }

//    @Test
//    public void testFindTeachersTeachingSubject() {
//        //1. Setup - precondiciones
//        final List<Object> teachersTeachingSubjectRaw = new ArrayList<>();
//        teachersTeachingSubjectRaw.add(new Object[] {USER_ID_ONE, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE});
//        teachersTeachingSubjectRaw.add(new Object[] {USER_ID_TWO, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE});
//        when(mockDao.getMostExpensiveUserFee(eq(SUBJECT_ONE))).thenReturn(MAXPRICE);
//        when(mockDao.filterUsers(eq(SUBJECT_ONE), eq(MAXPRICE), eq(ANY_LEVEL), eq(MAX_LEVEL), eq(ANY_RATING),
//                eq(RAND_ORDER), eq(GET_ALL))).thenReturn(teachersTeachingSubjectRaw);
//
//        //2. Ejercito la class under test una unica linea
//        final List<TeacherInfo> maybeTeacherInfoList = teachesService.findTeachersTeachingSubject(SUBJECT_ONE, GET_ALL.toString());
//
//        //3. Asserts - postcondiciones
//        final List<TeacherInfo> teacherInfoList = new ArrayList<>();
//        teacherInfoList.add(new TeacherInfo(USER_ID_ONE, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE));
//        teacherInfoList.add(new TeacherInfo(USER_ID_TWO, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE));
//        Assert.assertEquals(teacherInfoList, maybeTeacherInfoList);
//    }

    @Test
    public void testFilterUsers() {
        //1. Setup - precondiciones
        final TeacherInfo teacherOne = new TeacherInfo.Builder(MAIL_ONE, USERNAME, USER_ID_ONE).maxPrice(MAXPRICE)
                .minPrice(MINPRICE).rate(RATE).build();
        final TeacherInfo teacherTwo = new TeacherInfo.Builder(MAIL_TWO, USERNAME, USER_ID_TWO).maxPrice(MAXPRICE)
                .minPrice(MINPRICE).rate(RATE).build();
        final List<TeacherInfo> teachers = new ArrayList<TeacherInfo>() {{
            add(teacherOne);
            add(teacherTwo);
        }};
        final PageRequest pageRequest = new PageRequest(PAGE, PAGE_SIZE);
        final int total = teachers.size() / pageRequest.getPageSize() + (teachers.size() % pageRequest.getPageSize() > 0 ? 1: 0);
        final Page<TeacherInfo> expectedTeacherInfoPage = new Page<>(teachers, PAGE_SIZE, PAGE, total);
        when(teachesMockDao.getMostExpensiveUserFee(eq(SUBJECT_ONE))).thenReturn(MAXPRICE);
        when(teachesMockDao.filterUsers(eq(SUBJECT_ONE), eq(MAXPRICE), eq(ANY_LEVEL), eq(MAX_LEVEL), eq(ANY_RATING),
                eq(RAND_ORDER), any(PageRequest.class))).thenReturn(new Page<>(new ArrayList<TeacherInfo>() {{
            add(teacherOne);
            add(teacherTwo);
        }}, PAGE_SIZE, PAGE, total));

        //2. Ejercito la class under test una unica linea
        final Page<TeacherInfo> teacherInfoPage = teachesService.filterUsers(SUBJECT_ONE, RAND_ORDER, OVER_PRICE,
                ANY_LEVEL, ANY_RATING, PAGE, PAGE_SIZE);

        //3. Asserts - postcondiciones
        Assert.assertEquals(expectedTeacherInfoPage, teacherInfoPage);
    }

    @Test
    public void testGetSubjectAndLevelsTaughtByUser() {
        User teacher = new User.Builder(MAIL_ONE).userId(USER_ID_ONE).build();
        Subject subject = new Subject(SUBJECT_ONE, SUBJECT_ID_ONE);
        Teaches teachesOne = new Teaches.Builder().teacher(teacher).subject(subject).level(LEVEL_ONE).build();
        Teaches teachesTwo = new Teaches.Builder().teacher(teacher).subject(subject).level(LEVEL_TWO).build();
        when(teachesMockDao.get(eq(USER_ID_ONE))).thenReturn(new ArrayList<Teaches>(){{
            add(teachesOne);
            add(teachesTwo);
        }});

        Map<Subject, List<Integer>> expectedSubjectsAndLevels = new HashMap<>();
        expectedSubjectsAndLevels.put(subject, new ArrayList<>(Arrays.asList(teachesOne.getLevel(),teachesTwo.getLevel())));

        final Map<Subject, List<Integer>> subjectsAndLevels = teachesService.getSubjectAndLevelsTaughtByUser(USER_ID_ONE);
        Assert.assertEquals(expectedSubjectsAndLevels, subjectsAndLevels);
    }

    @Test
    public void testGetSubjectAndLevelsAvailableForUser() {
        User teacher = new User.Builder(MAIL_ONE).userId(USER_ID_ONE).build();
        Subject subjectOne = new Subject(SUBJECT_ONE, SUBJECT_ID_ONE);
        Subject subjectTwo = new Subject(SUBJECT_TWO, SUBJECT_ID_TWO);
        Teaches teachesOne = new Teaches.Builder().teacher(teacher).subject(subjectOne).level(LEVEL_ONE).build();
        Teaches teachesTwo = new Teaches.Builder().teacher(teacher).subject(subjectOne).level(LEVEL_TWO).build();
        Teaches teachesThree = new Teaches.Builder().teacher(teacher).subject(subjectTwo).level(LEVEL_ONE).build();
        Teaches teachesFour = new Teaches.Builder().teacher(teacher).subject(subjectTwo).level(LEVEL_THREE).build();
        when(subjectMockDao.listSubjects()).thenReturn(new ArrayList<Subject>() {{
            add(subjectOne);
            add(subjectTwo);
        }});
        when(teachesMockDao.get(eq(USER_ID_ONE))).thenReturn(new ArrayList<Teaches>(){{
            add(teachesOne);
            add(teachesTwo);
            add(teachesThree);
            add(teachesFour);
        }});

        Map<Subject, List<Integer>> expectedAvailableSubjects = new HashMap<>();
        expectedAvailableSubjects.put(subjectOne, new ArrayList<>(Arrays.asList(LEVEL_FOUR, LEVEL_THREE)));
        expectedAvailableSubjects.put(subjectTwo, new ArrayList<>(Arrays.asList(LEVEL_FOUR, LEVEL_TWO)));

        final Map<Subject, List<Integer>> availableSubjects = teachesService.getSubjectAndLevelsAvailableForUser(USER_ID_ONE);

        Assert.assertEquals(expectedAvailableSubjects, availableSubjects);
    }

}
