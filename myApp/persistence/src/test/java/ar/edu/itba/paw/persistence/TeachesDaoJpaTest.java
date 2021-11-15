package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.Rating;
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
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class TeachesDaoJpaTest {

    private static final double DELTA = 1e-15;
    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USERNAME_EXTRA = "Jane Doe";
    private static final String USER_MAIL_EXTRA = "jane@doe.com";
    private static final String USER_PASS = "1234";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final String SCHEDULE = "todos los dias habiles de 8 a 16";
    private static final String REVIEW = "Muy buen profesor";
    private static final Long USER_ID = 1L;
    private static final String SUBJECT_ONE = "MATE 1";
    private static final String SUBJECT_TWO = "MATE 2";
    private static final Long SUBJECT_ID_ONE = 1L;
    private static final Integer PRICE = 500;
    private static final Integer MAX_LEVEL = 3;
    private static final Integer MIN_LEVEL = 1;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final Integer RATE = 4;
    private static final Integer FILTER_RATE = 3;
    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0, GET_ALL = 0;

    @Autowired
    private TeachesDao teachesDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    private User user;
    private Subject subject;
    private Teaches teaches;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"teaches");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"rating");
        user = new User(USERNAME, USER_PASS, null, USER_MAIL, DESCRIPTION, SCHEDULE);
        entityManager.persist(user);
        subject = new Subject(SUBJECT_ONE, null);
        entityManager.persist(subject);
        teaches = new Teaches(user, subject, PRICE, MAX_LEVEL);
    }

    @Test
    @Rollback
    public void testAddSubjectToUser() {
        final Teaches maybeTeaches = teachesDao.addSubjectToUser(USER_ID, SUBJECT_ID_ONE, PRICE, MAX_LEVEL);

        Assert.assertNotNull(maybeTeaches);
        Assert.assertEquals(teaches, maybeTeaches);
    }

    @Test
    @Rollback
    public void testRemoveSubjectToUser() {
        entityManager.persist(teaches);
        final int rowsModified = teachesDao.removeSubjectToUser(USER_ID, SUBJECT_ID_ONE, MAX_LEVEL);

        Assert.assertEquals(1, rowsModified);
    }

    @Test
    @Rollback
    public void testRemoveSubjectToUserFalse() {
        final int rowsModified = teachesDao.removeSubjectToUser(USER_ID, SUBJECT_ID_ONE, MAX_LEVEL);

        Assert.assertEquals(0, rowsModified);
    }

    @Test
    @Rollback
    public void testFindByUserAndSubjectAndLevel() {
        entityManager.persist(teaches);
        final Optional<Teaches> maybeTeaches = teachesDao.findByUserAndSubjectAndLevel(USER_ID, SUBJECT_ID_ONE, MAX_LEVEL);

        Assert.assertTrue(maybeTeaches.isPresent());
        Assert.assertEquals(teaches, maybeTeaches.get());
    }

    @Test
    @Rollback
    public void testGetListOfAllSubjectsTeachedByUser() {
        final Subject subjectExtra = new Subject(SUBJECT_TWO, null);
        entityManager.persist(subjectExtra);
        entityManager.persist(teaches);
        final Teaches teachesExtra = new Teaches(user, subjectExtra, PRICE, MAX_LEVEL);
        entityManager.persist(teachesExtra);
        final List<Subject> subjects = teachesDao.getListOfAllSubjectsTeachedByUser(USER_ID);

        final List<Subject> expectedSubjects = new ArrayList<>();
        expectedSubjects.add(subject);
        expectedSubjects.add(subjectExtra);

        Assert.assertEquals(expectedSubjects, subjects);
    }

    @Test
    @Rollback
    public void testGet() {
        final Subject subjectExtra = new Subject(SUBJECT_TWO, null);
        entityManager.persist(subjectExtra);
        entityManager.persist(teaches);
        final Teaches teachesExtra = new Teaches(user, subjectExtra, PRICE, MAX_LEVEL);
        entityManager.persist(teachesExtra);
        final List<Teaches> teachesList = teachesDao.get(USER_ID);

        final List<Teaches> expectedTeachesList = new ArrayList<>();
        expectedTeachesList.add(teaches);
        expectedTeachesList.add(teachesExtra);

        Assert.assertNotNull(teachesList);
        Assert.assertEquals(expectedTeachesList, teachesList);
    }

    @Test
    @Rollback
    public void testGetNone() {
        final List<Teaches> teachesList = teachesDao.get(USER_ID);

        Assert.assertEquals(0, teachesList.size());
    }

    @Test
    @Rollback
    public void testGetMaxPrice() {
        entityManager.persist(teaches);
        final Teaches teachesExtra = new Teaches(user, subject, MAXPRICE, ANY_LEVEL);
        entityManager.persist(teachesExtra);
        final Integer maxPrice = teachesDao.getMaxPrice(USER_ID);

        Assert.assertEquals(MAXPRICE, maxPrice);
    }

    @Test
    @Rollback
    public void testGetMinPrice() {
        entityManager.persist(teaches);
        final Teaches teachesExtra = new Teaches(user, subject, MAXPRICE, ANY_LEVEL);
        entityManager.persist(teachesExtra);
        final int minPrice = teachesDao.getMinPrice(USER_ID);

        Assert.assertEquals(teaches.getPrice(), minPrice);
    }

    @Test
    @Rollback
    public void testGetMostExpensiveUserFee() {
        final User userExtra = new User(USERNAME_EXTRA, USER_PASS, null, USER_MAIL_EXTRA, DESCRIPTION, SCHEDULE);
        entityManager.persist(userExtra);
        final Teaches teachesExtra = new Teaches(userExtra, subject, MAXPRICE, ANY_LEVEL);
        entityManager.persist(teachesExtra);
        final Integer maxFee = teachesDao.getMostExpensiveUserFee("Mat");

        Assert.assertEquals(MAXPRICE, maxFee);
    }

    @Test
    @Rollback
    public void testFilterUsers() {
        entityManager.persist(teaches);
        final User userExtra = new User(USERNAME_EXTRA, USER_PASS, null, USER_MAIL_EXTRA, DESCRIPTION, SCHEDULE);
        entityManager.persist(userExtra);
        final Rating rating = new Rating(RATE.floatValue(), REVIEW, user, userExtra);
        entityManager.persist(rating);
        final Teaches teachesExtra = new Teaches(userExtra, subject, MAXPRICE, MIN_LEVEL);
        entityManager.persist(teachesExtra);
        final List<Object> teachersFiltered = teachesDao.filterUsers(SUBJECT_ONE, MAXPRICE, MAX_LEVEL, MAX_LEVEL, FILTER_RATE, RAND_ORDER, GET_ALL);

        final Object[] expectedTeacherInfo = new Object[] {USER_ID, USERNAME, teaches.getPrice(), teaches.getPrice(), DESCRIPTION, RATE};

        teachersFiltered.forEach((teacherInfo) -> {
            Object[] teacherInfoRaw = (Object[]) teacherInfo;
            Assert.assertEquals(((Number) expectedTeacherInfo[0]).longValue(), ((Number) teacherInfoRaw[0]).longValue());
            Assert.assertEquals(expectedTeacherInfo[1].toString(), teacherInfoRaw[1].toString());
            Assert.assertEquals(((Number) expectedTeacherInfo[2]).intValue(), ((Number) teacherInfoRaw[2]).intValue());
            Assert.assertEquals(((Number) expectedTeacherInfo[3]).intValue(), ((Number) teacherInfoRaw[3]).intValue());
            Assert.assertEquals(expectedTeacherInfo[4].toString(), teacherInfoRaw[4].toString());
            Assert.assertEquals(((Number) expectedTeacherInfo[5]).floatValue(), ((Number) teacherInfoRaw[5]).floatValue(), DELTA);
        });
    }

    @Test
    @Rollback
    public void testGetTopRatedTeachers() {
//        for (int i = 1; i <= 6; i++) {
//            final User userExtra = new InstanceProvider().getNewUser(i);
//            entityManager.persist(userExtra);
//            final Rating rating;
//            if (i < 6) {
//                rating = new Rating(5f, REVIEW, userExtra, user);
//            }
//            else {
//                rating = new Rating(1f, REVIEW, userExtra, user);
//            }
//            entityManager.persist(rating);
//        }
//        final List<Object> topRatedTeachers = teachesDao.getTopRatedTeachers();
//
//        for (int i = 0; i < 5; i++) {
//            Object[] teacherInfoRaw = (Object[]) topRatedTeachers.get(i);
//            Assert.assertEquals((long) i + 1, ((Number) teacherInfoRaw[0]).longValue());
//        }
    }

}
