package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sun.security.ssl.SunJSSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeachesServiceImplTest {

    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USER_PASS = "1234";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final String SCHEDULE = "todos los dias habiles de 8 a 16";
    private static final Long USER_ID = 1L;
    private static final String SUBJECT_ONE = "MATE";
    private static final String SUBJECT_TWO = "FRANCES";
    private static final Long SUBJECT_ID_ONE = 1L;
    private static final Long SUBJECT_ID_TWO = 2L;
    private static final Integer PRICE = 500;
    private static final Integer LEVEL = 3;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final float RATE = 3.99f ;

    @InjectMocks
    private TeachesServiceImpl teachesService = new TeachesServiceImpl();

    @Mock
    private TeachesDao mockDao;

    private final User user = new User(USERNAME, USER_PASS, USER_ID, USER_MAIL, DESCRIPTION,SCHEDULE);
    private final Subject subjectOne = new Subject(SUBJECT_ONE, SUBJECT_ID_ONE);
    private final Subject subjectTwo = new Subject(SUBJECT_TWO, SUBJECT_ID_TWO);
    private final Teaches teachesOne = new Teaches(user, subjectOne, PRICE, LEVEL);
    private final Teaches teachesTwo = new Teaches(user, subjectTwo, PRICE, LEVEL);

    @Test
    public void testAddSubjectToUser() {
        //1. Setup - precondiciones
        when(mockDao.addSubjectToUser(eq(USER_ID), eq(SUBJECT_ID_ONE), eq(PRICE), eq(LEVEL))).thenReturn(teachesOne);

        //2. Ejercito la class under test una unica linea
        final Optional<Teaches> maybeTeaches = teachesService.addSubjectToUser(USER_ID, SUBJECT_ID_ONE, PRICE, LEVEL);

        //3. Asserts - postcondiciones
        Assert.assertTrue(maybeTeaches.isPresent());
        Assert.assertEquals(teachesOne, maybeTeaches.get());
    }

    @Test
    public void testRemoveSubjectToUser() {
        //1. Setup - precondiciones
        when(mockDao.removeSubjectToUser(eq(USER_ID), eq(SUBJECT_ID_ONE), eq(LEVEL))).thenReturn(1);

        //2. Ejercito la class under test una unica linea
        final int rowsModified = teachesService.removeSubjectToUser(USER_ID, SUBJECT_ID_ONE, LEVEL);

        //3. Asserts - postcondiciones
        Assert.assertEquals(1, rowsModified);
    }

    @Test
    public void testGetListOfAllSubjectsTeachedByUser() {
        //1. Setup - precondiciones
        when(mockDao.getListOfAllSubjectsTeachedByUser(eq(USER_ID))).thenReturn(Arrays.asList(subjectOne, subjectTwo));

        //2. Ejercito la class under test una unica linea
        final List<Subject> subjects = teachesService.getListOfAllSubjectsTeachedByUser(USER_ID);

        //3. Asserts - postcondiciones
        Assert.assertEquals(Arrays.asList(subjectOne, subjectTwo), subjects);
    }

    @Test
    public void testFindByUserAndSubjectAndLevel() {
        //1. Setup - precondiciones
        when(mockDao.findByUserAndSubjectAndLevel(eq(USER_ID), eq(SUBJECT_ID_ONE), eq(LEVEL))).thenReturn(Optional.of(teachesOne));

        //2. Ejercito la class under test una unica linea
        final Optional<Teaches> maybeTeaches = teachesService.findByUserAndSubjectAndLevel(USER_ID, SUBJECT_ID_ONE, LEVEL);

        //3. Asserts - postcondiciones
        Assert.assertTrue(maybeTeaches.isPresent());
        Assert.assertEquals(teachesOne, maybeTeaches.get());
    }

}
