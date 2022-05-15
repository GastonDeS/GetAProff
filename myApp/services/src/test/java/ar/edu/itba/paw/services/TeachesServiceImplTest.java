package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
    private static final Integer LEVEL = 3;
    private static final Integer PAGE = 1;
    private static final Integer PAGE_SIZE = 9;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final float RATE = 3.99f ;
    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0, MAX_LEVEL = 3, GET_ALL = 0, OVER_PRICE = 1500;

    @InjectMocks
    private TeachesServiceImpl teachesService = new TeachesServiceImpl();

    @Mock
    private TeachesDao mockDao;

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
        when(mockDao.getMostExpensiveUserFee(eq(SUBJECT_ONE))).thenReturn(MAXPRICE);
        when(mockDao.filterUsers(eq(SUBJECT_ONE), eq(MAXPRICE), eq(ANY_LEVEL), eq(MAX_LEVEL), eq(ANY_RATING),
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

}
