package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserDaoJPATest {

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
    private static final String SUBJECT_ONE = "MATE 1";


    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    private User user;
    private User user2;
    private Teaches teaches;
    private Subject subject;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"rating");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"favourites");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"teaches");
        user = new User(USERNAME, USER_PASS, null, USER_MAIL, DESCRIPTION, SCHEDULE);
        user2 = new User(USERNAME, USER_PASS, null, USER_MAIL, DESCRIPTION, SCHEDULE);
        subject = new Subject(SUBJECT_ONE, null);
        entityManager.persist(subject);
        teaches = new Teaches(user,subject,500,0);
    }

    @Test
    @Rollback
    public void testGetUser() {
        entityManager.persist(user);

        Optional<User> maybeUser = userDao.get(user.getId());

        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(user.getId(),maybeUser.get().getUserid());
    }

    @Test
    @Rollback
    public void testCreate() {
        User userCreated = userDao.create(USERNAME,USER_MAIL,USER_PASS,DESCRIPTION,SCHEDULE);
        entityManager.flush();

        Assert.assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"users"));
    }

    @Test
    @Rollback
    public void testIsFaved() {
        entityManager.persist(user);
        List<User> faves = new ArrayList<>();
        faves.add(user);
        user2.setFavourites(faves);
        entityManager.persist(user2);

        Boolean faved = userDao.isFaved(user.getId(),user2.getId());

        Assert.assertEquals(true,faved);
    }

    @Test
    @Rollback
    public void testAddFavourite() {
        entityManager.persist(user);
        entityManager.persist(user2);

        int added = userDao.addFavourite(user.getId(), user2.getId());
        entityManager.flush();

        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,
                "favourites","teacherid = "+user.getId()+" AND studentid = "+user2.getId()));
    }

    @Test
    @Rollback
    public void testRemoveFavourite() {
        entityManager.persist(user);
        List<User> faves = new ArrayList<>();
        faves.add(user);
        user2.setFavourites(faves);
        entityManager.persist(user2);

        int added = userDao.removeFavourite(user.getId(), user2.getId());
        entityManager.flush();


        Assert.assertEquals(1,added);
        Assert.assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,
                "favourites","teacherid = "+user.getId()+" AND studentid = "+user2.getId()));
    }

    @Test
    @Rollback
    public void testGetFavourites() {
        entityManager.persist(user);
        List<User> faves = new ArrayList<>();
        faves.add(user);
        user2.setFavourites(faves);
        entityManager.persist(user2);
        entityManager.persist(teaches);

        List<Object> favourites = userDao.getFavourites(user2.getId());

        final Object expectedId = (Object) user.getId();
        Assert.assertEquals(1,favourites.size());
        Assert.assertEquals( ((Number) expectedId).longValue()
                ,((Number)((Object[])favourites.get(0))[0]).longValue());
    }

    @Test
    @Rollback
    public void testFindByEmail() {
        entityManager.persist(user);

        Optional<User> userByEmail = userDao.findByEmail(user.getMail());

        Assert.assertTrue(userByEmail.isPresent());
        Assert.assertEquals(user, userByEmail.get());
    }

    @Test
    @Rollback
    public void testSetUserDescription() {
        entityManager.persist(user);

        int valid = userDao.setUserDescription(user.getId(), "New Description");
        entityManager.flush();

        Assert.assertEquals(1,valid);
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users","description = "+"\'New Description\'"));
    }

    @Test
    @Rollback
    public void testSetUserName() {
        entityManager.persist(user);

        int valid = userDao.setUserName(user.getId(), "NEWNAME");
        entityManager.flush();

        Assert.assertEquals(1,valid);
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users","name = "+"\'NEWNAME\'"));
    }

    @Test
    @Rollback
    public void testSetUserSchedule() {
        entityManager.persist(user);

        int valid = userDao.setUserSchedule(user.getId(), "NEWSCHEDULE");
        entityManager.flush();

        Assert.assertEquals(1,valid);
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users","schedule = "+"\'NEWSCHEDULE\'"));

    }



}
