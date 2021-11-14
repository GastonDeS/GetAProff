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
    private static final int USER_ROLE = 1;
    private static final String SUBJECT = "MATE";
    private static final Long SUBJECT_ID = 1L;
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
    private final Subject subject = new Subject(SUBJECT, SUBJECT_ID);
    private final Teaches teaches = new Teaches(user, subject, PRICE, LEVEL);

    @Test
    public void testAddSubjectToUser() {
        //1. Setup - precondiciones
        when(mockDao.addSubjectToUser(eq(USER_ID), eq(SUBJECT_ID), eq(PRICE), eq(LEVEL))).thenReturn(teaches);

        //2. Ejercito la class under test una unica linea
        final Optional<Teaches> maybeTeaches = teachesService.addSubjectToUser(USER_ID, SUBJECT_ID, PRICE, LEVEL);

        //3. Asserts - postcondiciones
        Assert.assertTrue(maybeTeaches.isPresent());
        Assert.assertEquals(teaches, maybeTeaches.get());
    }

}
