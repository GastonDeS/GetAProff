package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceImplTest {

    private static final String USER_MAIL = "John@Doe.com";
    private static final String USER_MAIL_EXTRA = "Jane@Doe.com";
    private static final Long USER_ID_ONE = 1L;
    private static final Long USER_ID_TWO = 2L;
    private static final String SUBJECT = "MATE 1";
    private static final Long SUBJECT_ID = 1L;
    private static final Long LECTURE_ID = 1L;
    private static final Integer STATUS_TWO = 2;
    private static final Integer STATUS_THREE = 3;
    private static final Integer STATUS_OTHER = 5;

    @InjectMocks
    private LectureServiceImpl lectureService = new LectureServiceImpl();

    @Mock
    private LectureDao mockDao;

    private User teacher, student;
    private Subject subjectOne;

    @Before
    public void setUp() {
        teacher = new User.Builder(USER_MAIL).userId(USER_ID_ONE).build();
        student = new User.Builder(USER_MAIL_EXTRA).userId(USER_ID_TWO).build();
        subjectOne = new Subject(SUBJECT, SUBJECT_ID);
    }

    @Test
    public void testFindClassesByStudentAndStatusThree() {
        //1. Setup - precondiciones
        final List<Lecture> lectureList = new ArrayList<>();
        lectureList.add(new Lecture.Builder(teacher, student, subjectOne).status(STATUS_THREE).classId(LECTURE_ID).build());
        when(mockDao.findClassesByStudentId(eq(USER_ID_TWO))).thenReturn(lectureList);
        when(mockDao.getNotificationsCount(LECTURE_ID, 1)).thenReturn(0);

        //2. Ejercito la class under test una unica linea
        final List<Lecture> maybeLectureList = lectureService.findClassesByStudentAndStatus(USER_ID_TWO, STATUS_THREE);

        //3. Asserts - postcondiciones
        Assert.assertEquals(lectureList, maybeLectureList);
    }

    @Test
    public void testFindClassesByStudentAndStatusTwo() {
        //1. Setup - precondiciones
        final List<Lecture> lectureList = new ArrayList<>();
        lectureList.add(new Lecture.Builder(teacher, student, subjectOne).status(STATUS_TWO).classId(LECTURE_ID).build());
        when(mockDao.findClassesByStudentAndMultipleStatus(eq(USER_ID_TWO), eq(STATUS_TWO))).thenReturn(lectureList);
        when(mockDao.getNotificationsCount(LECTURE_ID, 1)).thenReturn(0);

        //2. Ejercito la class under test una unica linea
        final List<Lecture> maybeLectureList = lectureService.findClassesByStudentAndStatus(USER_ID_TWO, STATUS_TWO);

        //3. Asserts - postcondiciones
        Assert.assertEquals(lectureList, maybeLectureList);
    }

    @Test
    public void testFindClassesByStudentAndStatusOther() {
        //1. Setup - precondiciones
        final List<Lecture> lectureList = new ArrayList<>();
        lectureList.add(new Lecture.Builder(teacher, student, subjectOne).status(STATUS_OTHER).classId(LECTURE_ID).build());
        when(mockDao.findClassesByStudentAndStatus(eq(USER_ID_TWO), eq(STATUS_OTHER))).thenReturn(lectureList);
        when(mockDao.getNotificationsCount(LECTURE_ID, 1)).thenReturn(0);

        //2. Ejercito la class under test una unica linea
        final List<Lecture> maybeLectureList = lectureService.findClassesByStudentAndStatus(USER_ID_TWO, STATUS_OTHER);

        //3. Asserts - postcondiciones
        Assert.assertEquals(lectureList, maybeLectureList);
    }

    @Test
    public void testFindClassesByTeacherAndStatusThree() {
        //1. Setup - precondiciones
        final List<Lecture> lectureList = new ArrayList<>();
        lectureList.add(new Lecture.Builder(teacher, student, subjectOne).status(STATUS_THREE).classId(LECTURE_ID).build());
        when(mockDao.findClassesByTeacherId(eq(USER_ID_ONE))).thenReturn(lectureList);
        when(mockDao.getNotificationsCount(LECTURE_ID, 0)).thenReturn(0);

        //2. Ejercito la class under test una unica linea
        final List<Lecture> maybeLectureList = lectureService.findClassesByTeacherAndStatus(USER_ID_ONE, STATUS_THREE);

        //3. Asserts - postcondiciones
        Assert.assertEquals(lectureList, maybeLectureList);
    }

    @Test
    public void testFindClassesByTeacherAndStatusTwo() {
        //1. Setup - precondiciones
        final List<Lecture> lectureList = new ArrayList<>();
        lectureList.add(new Lecture.Builder(teacher, student, subjectOne).status(STATUS_TWO).classId(LECTURE_ID).build());
        when(mockDao.findClassesByTeacherAndMultipleStatus(eq(USER_ID_ONE), eq(STATUS_TWO))).thenReturn(lectureList);
        when(mockDao.getNotificationsCount(LECTURE_ID, 0)).thenReturn(0);

        //2. Ejercito la class under test una unica linea
        final List<Lecture> maybeLectureList = lectureService.findClassesByTeacherAndStatus(USER_ID_ONE, STATUS_TWO);

        //3. Asserts - postcondiciones
        Assert.assertEquals(lectureList, maybeLectureList);
    }

    @Test
    public void testFindClassesByTeacherAndStatusOther() {
        //1. Setup - precondiciones
        final List<Lecture> lectureList = new ArrayList<>();
        lectureList.add(new Lecture.Builder(teacher, student, subjectOne).status(STATUS_OTHER).classId(LECTURE_ID).build());
        when(mockDao.findClassesByTeacherAndStatus(eq(USER_ID_ONE), eq(STATUS_OTHER))).thenReturn(lectureList);
        when(mockDao.getNotificationsCount(LECTURE_ID, 0)).thenReturn(0);

        //2. Ejercito la class under test una unica linea
        final List<Lecture> maybeLectureList = lectureService.findClassesByTeacherAndStatus(USER_ID_ONE, STATUS_OTHER);

        //3. Asserts - postcondiciones
        Assert.assertEquals(lectureList, maybeLectureList);
    }

}
