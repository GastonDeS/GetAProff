package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
import ar.edu.itba.paw.models.Lecture;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class LectureDaoJpaTest {

    @Autowired
    private LectureDao lectureDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    private User student, teacher;
    private Subject subject;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"teaches");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"rating");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"classes");
        teacher = InstanceProvider.getNewUser(1);
        student = InstanceProvider.getNewUser(2);
        subject = InstanceProvider.getSubject();
        entityManager.persist(teacher);
        entityManager.persist(student);
        entityManager.persist(subject);
    }

    @Test
    @Rollback
    public void testGet() {
        final Lecture expectedLecture = InstanceProvider.getNewLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final Optional<Lecture> lecture = lectureDao.get(expectedLecture.getClassId());

        Assert.assertEquals(Optional.of(expectedLecture), lecture);
    }

    @Test
    @Rollback
    public void testFindClassesByStudentId() {
        final Lecture expectedLecture = InstanceProvider.getNewLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final List<Lecture> lectureList = lectureDao.findClassesByStudentId(student.getId());
        final List<Lecture> expectedList = new ArrayList<>(Arrays.asList(expectedLecture));

        Assert.assertEquals(1, lectureList.size());
        Assert.assertEquals(expectedList, lectureList);
    }

    @Test
    @Rollback
    public void testFindClassesByStudentAndStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final List<Lecture> lectureList = lectureDao.findClassesByStudentAndStatus(student.getId(), expectedLecture.getStatus());
        final List<Lecture> expectedList = new ArrayList<>(Arrays.asList(expectedLecture));

        Assert.assertEquals(1, lectureList.size());
        Assert.assertEquals(expectedList, lectureList);
    }

    @Test
    @Rollback
    public void testFindClassesByStudentAndMultipleStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final List<Lecture> lectureList = lectureDao.findClassesByStudentAndMultipleStatus(student.getId(), expectedLecture.getStatus());
        final List<Lecture> expectedList = new ArrayList<>(Arrays.asList(expectedLecture));

        Assert.assertEquals(1, lectureList.size());
        Assert.assertEquals(expectedList, lectureList);
    }

    @Test
    @Rollback
    public void testFindClassesByTeacherId() {
        final Lecture expectedLecture = InstanceProvider.getNewLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final List<Lecture> lectureList = lectureDao.findClassesByTeacherId(teacher.getId());
        final List<Lecture> expectedList = new ArrayList<>(Arrays.asList(expectedLecture));

        Assert.assertEquals(1, lectureList.size());
        Assert.assertEquals(expectedList, lectureList);
    }

    @Test
    @Rollback
    public void testFindClassesByTeacherAndMultipleStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final List<Lecture> lectureList = lectureDao.findClassesByTeacherAndMultipleStatus(teacher.getId(), expectedLecture.getStatus());
        final List<Lecture> expectedList = new ArrayList<>(Arrays.asList(expectedLecture));

        Assert.assertEquals(1, lectureList.size());
        Assert.assertEquals(expectedList, lectureList);
    }

    @Test
    @Rollback
    public void testCreate() {
//        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
//
//        final Lecture lecture = lectureDao.create(student.getId(), teacher.getId(), expectedLecture.getLevel(), subject.getId(), expectedLecture.getPrice());
//
//        Assert.assertEquals(expectedLecture, lecture);
    }
}
