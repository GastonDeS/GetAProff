package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.*;
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
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class TeachesDaoJpaTest {

    private static final double DELTA = 1e-15;
    private static final Integer FILTER_RATE = 3, RAND_ORDER = 0, GET_ALL = 0, FILTER_PRICE = 10000;

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
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"classes");
        user = InstanceProvider.getUser();
        entityManager.persist(user);
        subject = InstanceProvider.getSubject();
        entityManager.persist(subject);
        teaches = InstanceProvider.getNewHighLevelTeaches(user, subject);
    }

    @Test
    @Rollback
    public void testAddSubjectToUser() {
        final Teaches maybeTeaches = teachesDao.addSubjectToUser(teaches.getTeacher().getId(), subject.getSubjectId(), teaches.getPrice(), teaches.getLevel());

        entityManager.flush();

        Assert.assertNotNull(maybeTeaches);
        Assert.assertEquals(teaches, maybeTeaches);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "teaches"));
    }

    @Test
    @Rollback
    public void testRemoveSubjectToUser() {
        entityManager.persist(teaches);
        final int rowsModified = teachesDao.removeSubjectToUser(teaches.getTeacher().getId(), subject.getSubjectId(), teaches.getLevel());

        Assert.assertEquals(1, rowsModified);
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "teaches"));
    }

    @Test
    @Rollback
    public void testRemoveSubjectToUserFalse() {
        final int rowsModified = teachesDao.removeSubjectToUser(teaches.getTeacher().getId(), subject.getSubjectId(), teaches.getLevel());

        Assert.assertEquals(0, rowsModified);
    }

    @Test
    @Rollback
    public void testFindByUserAndSubjectAndLevel() {
        entityManager.persist(teaches);
        final Optional<Teaches> maybeTeaches = teachesDao.findByUserAndSubjectAndLevel(teaches.getTeacher().getId(), subject.getSubjectId(), teaches.getLevel());

        Assert.assertTrue(maybeTeaches.isPresent());
        Assert.assertEquals(teaches, maybeTeaches.get());
    }

    @Test
    @Rollback
    public void testGetListOfAllSubjectsTeachedByUser() {
        final Subject subjectExtra = InstanceProvider.getNewSubject(2);
        entityManager.persist(subjectExtra);
        entityManager.persist(teaches);
        entityManager.persist(InstanceProvider.getNewTeaches(user, subjectExtra));
        final List<Subject> subjects = teachesDao.getListOfAllSubjectsTeachedByUser(user.getId());

        final List<Subject> expectedSubjects = new ArrayList<>();
        expectedSubjects.add(subject);
        expectedSubjects.add(subjectExtra);

        Assert.assertEquals(expectedSubjects, subjects);
    }

    @Test
    @Rollback
    public void testGet() {
        final Subject subjectExtra = InstanceProvider.getNewSubject(2);
        entityManager.persist(subjectExtra);
        entityManager.persist(teaches);
        final Teaches teachesExtra = InstanceProvider.getNewTeaches(user, subjectExtra);
        entityManager.persist(teachesExtra);
        final List<Teaches> teachesList = teachesDao.get(user.getId());

        final List<Teaches> expectedTeachesList = new ArrayList<>();
        expectedTeachesList.add(teaches);
        expectedTeachesList.add(teachesExtra);

        Assert.assertNotNull(teachesList);
        Assert.assertEquals(expectedTeachesList, teachesList);
    }

    @Test
    @Rollback
    public void testGetNone() {
        final List<Teaches> teachesList = teachesDao.get(user.getId());

        Assert.assertEquals(0, teachesList.size());
    }

    @Test
    @Rollback
    public void testGetMostExpensiveUserFee() {
        final User userExtra = InstanceProvider.getNewUser(1);
        entityManager.persist(userExtra);
        final Teaches teachesExtra = InstanceProvider.getNewHighPriceTeaches(userExtra, subject);
        entityManager.persist(teachesExtra);
        final int maxFee = teachesDao.getMostExpensiveUserFee("Mat");

        Assert.assertEquals(teachesExtra.getPrice(), maxFee);
    }

    @Test
    @Rollback
    public void testFilterUsers() {
        entityManager.persist(teaches);
        final User userExtra = InstanceProvider.getNewUser(1);
        entityManager.persist(userExtra);
        final Rating rating = InstanceProvider.getNewHighRating(user, userExtra);
        entityManager.persist(rating);
        final Teaches teachesExtra = InstanceProvider.getNewLowLevelTeaches(userExtra, subject);
        entityManager.persist(teachesExtra);
        PageRequest pageRequest = InstanceProvider.getNewPageRequest();
        final Page<TeacherInfo> teachersFiltered = teachesDao.filterUsers(teaches.getSubject().getName(), FILTER_PRICE, teaches.getLevel(), teaches.getLevel(), FILTER_RATE, RAND_ORDER, pageRequest);

        TeacherInfo teacherInfo = InstanceProvider.getNewTeacherInfo(user, rating.getRate());
        final Page<TeacherInfo> expectedTeachersFiltered = InstanceProvider.getNewPage(new ArrayList<>(Collections.singletonList(teacherInfo)), pageRequest);
        Assert.assertEquals(expectedTeachersFiltered, teachersFiltered);
    }

    @Test
    @Rollback
    public void testGetTopRatedTeachers() {
        List<TeacherInfo> expectedTopRatedTeachers = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            final User userExtra = InstanceProvider.getNewUser(i);
            entityManager.persist(userExtra);
            final Rating rating = i < 5 ? InstanceProvider.getNewHighRating(userExtra, user) :
                    InstanceProvider.getNewLowRating(userExtra, user);
            entityManager.persist(rating);
            entityManager.persist(InstanceProvider.getNewTeaches(userExtra, subject));
            if (i < 5) {
                expectedTopRatedTeachers.add(InstanceProvider.getNewTeacherInfo(userExtra, rating.getRate()));
            }
        }
        final List<TeacherInfo> topRatedTeachers = teachesDao.getTopRatedTeachers();
        Assert.assertEquals(expectedTopRatedTeachers, topRatedTeachers);
    }

    @Test
    @Rollback
    public void testGetMostRequested() {
        List<TeacherInfo> expectedMostRequestedTeachers = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            final User userExtra = InstanceProvider.getNewUser(i);
            entityManager.persist(userExtra);
            if (i < 5) {
                final Lecture lectureExtra = InstanceProvider.getNewLecture(userExtra, user, subject);
                entityManager.persist(lectureExtra);
            }
            entityManager.persist(InstanceProvider.getNewTeaches(userExtra, subject));
            if (i < 5) {
                expectedMostRequestedTeachers.add(InstanceProvider.getNewTeacherInfo(userExtra, 0F));
            }
        }
        final List<TeacherInfo> mostRequestedTeachers = teachesDao.getMostRequested();
        for (int t = 0; t < 4; t++) {
            System.out.println(mostRequestedTeachers.get(t).getDescription().equals(expectedMostRequestedTeachers.get(t).getDescription()));
        }
        Assert.assertEquals(expectedMostRequestedTeachers, mostRequestedTeachers);
    }

    @Test
    @Rollback
    public void testGetTeacherInfo() {
        final Subject subjectExtra = InstanceProvider.getNewSubject(2);
        entityManager.persist(subjectExtra);
        entityManager.persist(teaches);
        final Rating rating = InstanceProvider.getNewHighRating(user, InstanceProvider.getNewUser(1));
        entityManager.persist(rating);
        final Teaches teachesExtra = InstanceProvider.getNewTeaches(user, subjectExtra);
        entityManager.persist(teachesExtra);

        final Optional<TeacherInfo> teacherInfo = teachesDao.getTeacherInfo(user.getId());
        Assert.assertTrue(teacherInfo.isPresent());

        final TeacherInfo expectedTeacherInfo = InstanceProvider.getNewTeacherInfo(user, rating.getRate());
        Assert.assertEquals(expectedTeacherInfo, teacherInfo.get());
    }

}
