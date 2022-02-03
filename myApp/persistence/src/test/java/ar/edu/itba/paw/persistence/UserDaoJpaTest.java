package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.providers.InstanceProvider;
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
public class UserDaoJpaTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    private User user;
    private User userExtra;
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
        user = InstanceProvider.getUser();
        userExtra = InstanceProvider.getNewUser(2);
        subject = InstanceProvider.getSubject();
        entityManager.persist(subject);
        teaches = InstanceProvider.getNewTeaches(user, subject);
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
        userDao.create(user.getName(),user.getMail(), user.getPassword(), user.getDescription(), user.getSchedule());
        entityManager.flush();

        Assert.assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"users"));
    }

    @Test
    @Rollback
    public void testIsFaved() {
        entityManager.persist(user);
        List<User> faves = new ArrayList<>();
        faves.add(user);
        userExtra.setFavourites(faves);
        entityManager.persist(userExtra);

        Boolean faved = userDao.isFaved(user.getId(), userExtra.getId());

        Assert.assertEquals(true,faved);
    }

    @Test
    @Rollback
    public void testAddFavourite() {
        entityManager.persist(user);
        entityManager.persist(userExtra);

        userDao.addFavourite(user.getId(), userExtra.getId());
        entityManager.flush();

        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,
                "favourites","teacherid = " + user.getId() + " AND studentid = " + userExtra.getId()));
    }

    @Test
    @Rollback
    public void testRemoveFavourite() {
        entityManager.persist(user);
        List<User> faves = new ArrayList<>();
        faves.add(user);
        userExtra.setFavourites(faves);
        entityManager.persist(userExtra);

        int added = userDao.removeFavourite(user.getId(), userExtra.getId());
        entityManager.flush();

        Assert.assertEquals(1,added);
        Assert.assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,
                "favourites","teacherid = " + user.getId() + " AND studentid = " + userExtra.getId()));
    }

//    @Test
//    @Rollback
//    public void testGetFavourites() {
//        entityManager.persist(user);
//        List<User> faves = new ArrayList<>();
//        faves.add(user);
//        userExtra.setFavourites(faves);
//        entityManager.persist(userExtra);
//        entityManager.persist(teaches);
//
////        List<Object> favourites = userDao.getFavourites(userExtra.getId());
//
//        Assert.assertEquals(1,favourites.size());
//        Assert.assertEquals( (long) user.getId()
//                ,((Number)((Object[])favourites.get(0))[0]).longValue());
//    }

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

        Assert.assertEquals(1, valid);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users","description = "+"\'New Description\'"));
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
