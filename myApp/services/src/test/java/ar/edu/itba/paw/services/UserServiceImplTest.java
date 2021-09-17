package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    public static final String USERNAME = "John Doe";
    public static final String USER_MAIL = "John@Doe.com";
    public static final String USER_PASS = "1234";
    public static final int USER_ID = 1;
    public static final int USER_ROLE = 1;

    @InjectMocks
    private  UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao mockDao;

    @Test
    public void testCreate(){
        //Setup
        Mockito.when(mockDao.create(eq(USERNAME), eq(USER_MAIL), eq(USER_PASS), eq(USER_ROLE))).thenReturn(new User(USERNAME,USER_MAIL,USER_PASS));
        //Test
        final User user = userService.create(USERNAME, USER_MAIL, USER_PASS, USER_ROLE);
        //Postconditions
        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getName());
        Assert.assertEquals(USER_MAIL, user.getMail());
    }

    @Test
    public void findById(){
        //Setup
        Mockito.when(mockDao.get(eq(USER_ID))).thenReturn(new User(USERNAME,USER_PASS,USER_ID,USER_MAIL,USER_ROLE));
        //Test
        final User possibleUser = userService.findById(USER_ID);
        //Postconditions
        Assert.assertNotNull(possibleUser);
        Assert.assertEquals(USER_ID,possibleUser.getId());
    }
}
