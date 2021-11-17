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
        final List<Object> teachersFiltered = teachesDao.filterUsers(teaches.getSubject().getName(), FILTER_PRICE, teaches.getLevel(), teaches.getLevel(), FILTER_RATE, RAND_ORDER, GET_ALL);

        final Object[] expectedTeacherInfo = new Object[] {user.getId(), user.getName(), teaches.getPrice(), teaches.getPrice(), user.getDescription(), rating.getRate()};

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
        for (int i = 1; i < 6; i++) {
            final User userExtra = InstanceProvider.getNewUser(i);
            entityManager.persist(userExtra);
            final Rating rating = i < 5 ? InstanceProvider.getNewHighRating(userExtra, user) :
                    InstanceProvider.getNewLowRating(userExtra, user);
            entityManager.persist(rating);
            entityManager.persist(InstanceProvider.getNewTeaches(userExtra, subject));
        }
        final List<Object> topRatedTeachers = teachesDao.getTopRatedTeachers();

        Assert.assertEquals(4, topRatedTeachers.size());
        for (int i = 0; i < 4; i++) {
            Object[] teacherInfoRaw = (Object[]) topRatedTeachers.get(i);
            Assert.assertEquals("user " + (i + 1), teacherInfoRaw[1].toString());
        }
    }

    @Test
    @Rollback
    public void testGetMostRequested() {
        for (int i = 1; i < 6; i++) {
            final User userExtra = InstanceProvider.getNewUser(i);
            entityManager.persist(userExtra);
            if (i < 5) {
                final Lecture lectureExtra = InstanceProvider.getNewLecture(userExtra, user, subject);
                entityManager.persist(lectureExtra);
            }
            entityManager.persist(InstanceProvider.getNewTeaches(userExtra, subject));
        }
        final List<Object> mostRequested = teachesDao.getMostRequested();

        Assert.assertEquals(4, mostRequested.size());
        for (int i = 0; i < 4; i++) {
            Object[] teacherInfoRaw = (Object[]) mostRequested.get(i);
            Assert.assertEquals("user " + (i + 1), teacherInfoRaw[1].toString());
        }
    }

    @Test
    @Rollback
    public void testGetSubjectInfoListByUser() {
        entityManager.persist(teaches);
        final List<Object> subjectInfoList = teachesDao.getSubjectInfoListByUser(user.getId());

        Object[] subjectInfo = (Object[]) subjectInfoList.get(0);

        Assert.assertEquals(1, subjectInfoList.size());
        Assert.assertEquals((long) subject.getSubjectId(), ((Number) subjectInfo[0]).longValue());
        Assert.assertEquals(subject.getName(), subjectInfo[1].toString());
        Assert.assertEquals(teaches.getPrice(), ((Number) subjectInfo[2]).intValue());
        Assert.assertEquals(teaches.getLevel(), ((Number) subjectInfo[3]).intValue());
    }

}
