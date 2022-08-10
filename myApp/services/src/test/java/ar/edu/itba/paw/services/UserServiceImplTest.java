package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.InsertException;
import ar.edu.itba.paw.models.utils.Utility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USER_PASS = "1234";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final String SCHEDULE = "todos los dias habiles de 8 a 16";
    private static final Long USER_ID = 1L;
    private static final Long USER_ROLE = 1L;

    @InjectMocks
    private  UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao mockDao;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final User userMocked = new User(USERNAME,USER_PASS, (long) USER_ID,USER_MAIL,DESCRIPTION,SCHEDULE);

    @Test
    public void testCreate(){
//        1 setup - precondiciones
        when(passwordEncoder.encode(USER_PASS)).thenReturn(USER_PASS);
        when(mockDao.create(eq(Utility.capitalizeString(USERNAME)),eq(USER_MAIL),eq(USER_PASS),eq(DESCRIPTION),eq(SCHEDULE))).thenReturn(userMocked);

//        2 ejercito la class under test una unica linea
        Optional<User> user = userService.create(USERNAME,USER_MAIL,USER_PASS,DESCRIPTION,SCHEDULE,USER_ROLE);

//        3 Asserts - postcondiciones
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(user.get(), userMocked);
    }

    @Test(expected = DuplicateKeyException.class)
    public void testCreateDuplicateUser(){
//        1 setup - precondiciones
        when(mockDao.create(eq(USERNAME),eq(USER_MAIL),eq(USER_PASS),eq(DESCRIPTION), eq(SCHEDULE))).thenThrow(DuplicateKeyException.class);
        when(passwordEncoder.encode(eq(USER_PASS))).thenReturn(USER_PASS);
//        when(utilsService.capitalizeString(eq(USERNAME))).thenReturn(USERNAME);

//        2 ejercito la class under test una unica linea

        userService.create(USERNAME,USER_MAIL,USER_PASS,DESCRIPTION,SCHEDULE,(long) USER_ROLE);

//        3 Asserts - postcondiciones

        Assert.fail("Duplicate user creation should have thrown");
    }

    @Test(expected = InsertException.class)
    public void testAddFavourite() {
        when(mockDao.addFavourite(eq(USER_ID),eq(2L)/* second id*/)).thenThrow(InsertException.class);

        userService.addFavourite(USER_ID,2L/* second id*/);

        Assert.fail("Duplicate key should have thrown");
    }
}
