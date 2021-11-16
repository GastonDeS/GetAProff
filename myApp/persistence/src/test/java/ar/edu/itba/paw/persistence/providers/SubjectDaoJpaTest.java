package ar.edu.itba.paw.persistence.providers;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
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
public class SubjectDaoJpaTest {
    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USER_PASS = "1234";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final String SCHEDULE = "todos los dias habiles de 8 a 16";
    private static final String SUBJECT_ONE = "MATE 1";

    private Subject subject;
    private User user;
    private User user2;

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
        subject = new Subject(SUBJECT_ONE, null);
        user = new User(USERNAME, USER_PASS, null, USER_MAIL, DESCRIPTION, SCHEDULE);
        user2 = new User(USERNAME, USER_PASS, null, USER_MAIL, DESCRIPTION, SCHEDULE);
    }

    @Test
    @Rollback
    public void testFindById() {
        entityManager.persist(subject);

        Optional<Subject> maybeSubject = subjectDao.findById(subject.getSubjectId());

        Assert.assertTrue(maybeSubject.isPresent());
        Assert.assertEquals(subject,maybeSubject.get());
    }

    @Test
    @Rollback
    public void testCreate() {
        subjectDao.create(subject.getName());
        entityManager.flush();

        Assert.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"subject","name = \'"+subject.getName()+"\'"));
    }

    @Test
    @Rollback
    public void testListSubjects() {
        entityManager.persist(subject);

        List<Subject> subjectDaoList = subjectDao.listSubjects();

        Assert.assertEquals(1,subjectDaoList.size());
        Assert.assertEquals(subject,subjectDaoList.get(0));
    }

    @Test
    @Rollback
    public void testFindByName() {
        entityManager.persist(subject);

        Optional<Subject> maybeSubject = subjectDao.findByName(subject.getName());

        Assert.assertTrue(maybeSubject.isPresent());
        Assert.assertEquals(subject,maybeSubject.get());
    }

    @Test
    @Rollback
    public void testGetSubjectsMatching() {
        entityManager.persist(subject);

        List<Subject> subjects = subjectDao.getSubjectsMatching(subject.getName().substring(1,4));

        Assert.assertEquals(1,subjects.size());
        Assert.assertEquals(subject, subjects.get(0));
    }

    @Test
    @Rollback
    public void testGetHottestSubjects() {
        Teaches teaches1 = new Teaches(user,subject,500,0);
        Teaches teaches2 = new Teaches(user2,subject,500,0);
        List<Teaches> teachesList = new ArrayList<>();
        teachesList.add(teaches1);
        teachesList.add(teaches2);
        subject.setTeachersTeachingSubject(teachesList);
        entityManager.persist(subject);

        List<Subject> hottestSubjects =  subjectDao.getHottestSubjects();

        Assert.assertEquals(1,hottestSubjects.size());
        Assert.assertTrue(hottestSubjects.contains(subject));
    }
}
