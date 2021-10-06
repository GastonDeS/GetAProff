package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private static final int USER_ID = 1;
    private static final int USER_ROLE = 1;
    private static final String SUBJECT = "MATE";
    private static final Integer PRICE = 500;
    private static final Integer LEVEL = 3;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final float RATE = 3.99f ;

    @InjectMocks
    private  UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao mockDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    @Mock
    private UserDetailsService userDetailsService;

    private User u = new User(USERNAME,USER_PASS,USER_ID,USER_MAIL,DESCRIPTION,SCHEDULE);;


//    @Before
//    public void setUp() {
//        mockDao = Mockito.mock(UserDao.class);
//        passwordEncoder = Mockito.mock(PasswordEncoder.class);
//        roleService = Mockito.mock(RoleService.class);
//        userDetailsService = Mockito.mock(UserDetailsService.class);
//        u = new User(USERNAME,USER_PASS,USER_ID,USER_MAIL,DESCRIPTION,SCHEDULE);
//        userService.setUserDao(mockDao);
//        userService.setPasswordEncoder(passwordEncoder);
//        userService.setRoleService(roleService);
//        userService.setUserDetailsService(userDetailsService);
//    }

//    @Test
//    public void testCreate(){
////        1 setup - precondiciones
//        when(mockDao.create(eq(USERNAME),eq(USER_MAIL),eq(USER_PASS),eq(DESCRIPTION), eq(SCHEDULE))).thenReturn(u);
//        when(passwordEncoder.encode(eq(USER_PASS))).thenReturn(USER_PASS);
//        List<Role> roles = new ArrayList<>();
//        roles.add(new Role(USER_ID,String.valueOf(USER_ROLE)));
//        when(roleService.setUserRoles(USER_ID,USER_ROLE)).thenReturn(roles);
//
//        UserDetails userD = Mockito.mock(UserDetails.class);
//
//        when(userDetailsService.loadUserByUsername(eq(USER_MAIL))).thenReturn(userD);
//
////        2 ejercito la class under test una unica linea
//
//        final Optional<User> user = userService.create(USERNAME,USER_MAIL,USER_PASS,DESCRIPTION,SCHEDULE,USER_ROLE);
//
////        3 Asserts - postcondiciones
//
//        Assert.assertTrue(user.isPresent());
//        Assert.assertEquals(user.get().getMail(),USER_MAIL);
//    }

    @Test(expected = RuntimeException.class) //TODO
    public void testCreateDuplicateUser(){
//        1 setup - precondiciones
        User u = new User(USERNAME,USER_PASS,USER_ID,USER_MAIL,DESCRIPTION,SCHEDULE);
//        when(mockDao.create(eq(USERNAME),eq(USER_MAIL),eq(USER_PASS),eq(DESCRIPTION), eq(SCHEDULE))).thenThrow(RuntimeException.class);
//        when(passwordEncoder.encode(eq(USER_PASS))).thenReturn(USER_PASS);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(USER_ID,String.valueOf(USER_ROLE)));
//        when(roleService.setUserRoles(USER_ID,USER_ROLE)).thenReturn(roles);
//        2 ejercito la class under test una unica linea

        final Optional<User> user = userService.create(USERNAME,USER_MAIL,USER_PASS,DESCRIPTION,SCHEDULE,USER_ROLE);

//        3 Asserts - postcondiciones

        Assert.fail("Duplicate user creation should have thrown");
    }

    @Test
    public void testGetUserDescription() {
        when(mockDao.get(eq(USER_ID))).thenReturn(u);

        String description = userService.getUserDescription(USER_ID);

        Assert.assertEquals(DESCRIPTION,description);
    }

    @Test
    public void testMostExpensiveUserFee() {
        List<CardProfile> cards = new ArrayList<>();
        cards.add(new CardProfile(USER_ID,USERNAME,MAXPRICE,MINPRICE,DESCRIPTION,0,RATE));
        when(mockDao.filterUsers(eq(SUBJECT),eq(0),eq(Integer.MAX_VALUE),eq(0)/* any_level */,eq(0),eq(0))).thenReturn(cards);

        Integer max = userService.mostExpensiveUserFee(SUBJECT);

        Assert.assertEquals(MAXPRICE, max);
    }
}
