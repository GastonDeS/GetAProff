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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeachesServiceImplTest {

    private static final String USERNAME = "John Doe";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final Long USER_ID_ONE = 1L;
    private static final Long USER_ID_TWO = 2L;
    private static final String SUBJECT_ONE = "MATE 1";
    private static final String SUBJECT_TWO = "MATE 2";
    private static final Long SUBJECT_ID_ONE = 1L;
    private static final Long SUBJECT_ID_TWO = 2L;
    private static final Integer PRICE = 500;
    private static final Integer LEVEL = 3;
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

    @Test
    public void testFindTeachersTeachingSubject() {
        //1. Setup - precondiciones
        final List<Object> teachersTeachingSubjectRaw = new ArrayList<>();
        teachersTeachingSubjectRaw.add(new Object[] {USER_ID_ONE, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE});
        teachersTeachingSubjectRaw.add(new Object[] {USER_ID_TWO, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE});
        when(mockDao.getMostExpensiveUserFee(eq(SUBJECT_ONE))).thenReturn(MAXPRICE);
        when(mockDao.filterUsers(eq(SUBJECT_ONE), eq(MAXPRICE), eq(ANY_LEVEL), eq(MAX_LEVEL), eq(ANY_RATING),
                eq(RAND_ORDER), eq(GET_ALL))).thenReturn(teachersTeachingSubjectRaw);

        //2. Ejercito la class under test una unica linea
        final List<CardProfile> maybeCardProfileList = teachesService.findTeachersTeachingSubject(SUBJECT_ONE, GET_ALL.toString());

        //3. Asserts - postcondiciones
        final List<CardProfile> cardProfileList = new ArrayList<>();
        cardProfileList.add(new CardProfile(USER_ID_ONE, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE));
        cardProfileList.add(new CardProfile(USER_ID_TWO, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE));
        Assert.assertEquals(cardProfileList, maybeCardProfileList);
    }

    @Test
    public void testFilterUsers() {
        //1. Setup - precondiciones
        final List<Object> teachersTeachingSubjectRaw = new ArrayList<>();
        teachersTeachingSubjectRaw.add(new Object[] {USER_ID_ONE, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE});
        teachersTeachingSubjectRaw.add(new Object[] {USER_ID_TWO, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE});
        when(mockDao.getMostExpensiveUserFee(eq(SUBJECT_ONE))).thenReturn(MAXPRICE);
        when(mockDao.filterUsers(eq(SUBJECT_ONE), eq(MAXPRICE), eq(ANY_LEVEL), eq(MAX_LEVEL), eq(ANY_RATING),
                eq(RAND_ORDER), eq(GET_ALL))).thenReturn(teachersTeachingSubjectRaw);

        //2. Ejercito la class under test una unica linea
        final List<CardProfile> maybeCardProfileList = teachesService.filterUsers(SUBJECT_ONE, RAND_ORDER.toString(), OVER_PRICE.toString(),
                ANY_LEVEL.toString(), ANY_RATING.toString(), GET_ALL.toString());

        //3. Asserts - postcondiciones
        final List<CardProfile> cardProfileList = new ArrayList<>();
        cardProfileList.add(new CardProfile(USER_ID_ONE, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE));
        cardProfileList.add(new CardProfile(USER_ID_TWO, USERNAME, MAXPRICE, MINPRICE, DESCRIPTION, RATE));
        Assert.assertEquals(cardProfileList, maybeCardProfileList);
    }

}
