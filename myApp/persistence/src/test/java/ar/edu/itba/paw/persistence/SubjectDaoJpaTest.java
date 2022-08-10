package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
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
public class SubjectDaoJpaTest {

    private Subject subject;
    private User user;
    private User userExtra;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subject");
        subject = InstanceProvider.getSubject();
        user = InstanceProvider.getUser();
        userExtra = InstanceProvider.getNewUser(1);
    }

    @Test
    @Rollback
    public void testFindById() {
        entityManager.persist(subject);

        Optional<Subject> maybeSubject = subjectDao.findById(subject.getSubjectId());

        Assert.assertTrue(maybeSubject.isPresent());
        Assert.assertEquals(subject, maybeSubject.get());
    }

    @Test
    @Rollback
    public void testCreate() {
        subjectDao.create(subject.getName());
        entityManager.flush();

        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"subject","name = \'" + subject.getName() + "\'"));
    }

    @Test
    @Rollback
    public void testListSubjects() {
        entityManager.persist(subject);

        List<Subject> subjectDaoList = subjectDao.listSubjects();

        Assert.assertEquals(1, subjectDaoList.size());
        Assert.assertEquals(subject, subjectDaoList.get(0));
    }

    @Test
    @Rollback
    public void testFindByName() {
        entityManager.persist(subject);

        Optional<Subject> maybeSubject = subjectDao.findByName(subject.getName());

        Assert.assertTrue(maybeSubject.isPresent());
        Assert.assertEquals(subject, maybeSubject.get());
    }

    @Test
    @Rollback
    public void testGetSubjectsMatching() {
        entityManager.persist(subject);

        List<Subject> subjects = subjectDao.getSubjectsMatching(subject.getName().substring(1,4));

        Assert.assertEquals(1, subjects.size());
        Assert.assertEquals(subject, subjects.get(0));
    }

    @Test
    @Rollback
    public void testGetHottestSubjects() {
        Teaches teaches1 = InstanceProvider.getNewTeaches(user, subject);
        Teaches teaches2 = InstanceProvider.getNewTeaches(userExtra, subject);
        List<Teaches> teachesList = new ArrayList<>();
        teachesList.add(teaches1);
        teachesList.add(teaches2);
        subject.setTeachersTeachingSubject(teachesList);
        entityManager.persist(subject);

        List<Subject> hottestSubjects =  subjectDao.getHottestSubjects();

        Assert.assertEquals(1, hottestSubjects.size());
        Assert.assertTrue(hottestSubjects.contains(subject));
    }
}
