package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
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
    private Teaches teachesInfo;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"posts");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"classes");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject_files");
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

        PageRequest pageRequest = InstanceProvider.getNewPageRequest();
        final Page<Lecture> lectures = lectureDao.findClassesByStudentId(student.getId(), pageRequest);
        final Page<Lecture> expectedLectures = InstanceProvider.getNewPage(Collections.singletonList(expectedLecture), pageRequest);

        Assert.assertEquals(1, lectures.getContent().size());
        Assert.assertEquals(expectedLectures, lectures);
    }

    @Test
    @Rollback
    public void testFindClassesByStudentAndStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        PageRequest pageRequest = InstanceProvider.getNewPageRequest();
        final Page<Lecture> lectures = lectureDao.findClassesByStudentAndStatus(student.getId(), expectedLecture.getStatus(), pageRequest);
        final Page<Lecture> expectedLectures = InstanceProvider.getNewPage(Collections.singletonList(expectedLecture), pageRequest);

        Assert.assertEquals(1, lectures.getContent().size());
        Assert.assertEquals(expectedLectures, lectures);
    }

    @Test
    @Rollback
    public void testFindClassesByStudentAndMultipleStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        PageRequest pageRequest = InstanceProvider.getNewPageRequest();
        final Page<Lecture> lectures = lectureDao.findClassesByStudentAndMultipleStatus(student.getId(), expectedLecture.getStatus(), pageRequest);
        final Page<Lecture> expectedLectures = InstanceProvider.getNewPage(Collections.singletonList(expectedLecture), pageRequest);

        Assert.assertEquals(1, lectures.getContent().size());
        Assert.assertEquals(expectedLectures, lectures);
    }

    @Test
    @Rollback
    public void testFindClassesByTeacherId() {
        final Lecture expectedLecture = InstanceProvider.getNewLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        PageRequest pageRequest = InstanceProvider.getNewPageRequest();
        final Page<Lecture> lectures = lectureDao.findClassesByTeacherId(teacher.getId(), pageRequest);
        final Page<Lecture> expectedLectures = InstanceProvider.getNewPage(Collections.singletonList(expectedLecture), pageRequest);

        Assert.assertEquals(1, lectures.getContent().size());
        Assert.assertEquals(expectedLectures, lectures);
    }

    @Test
    @Rollback
    public void testFindClassesByTeacherAndStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        PageRequest pageRequest = InstanceProvider.getNewPageRequest();
        final Page<Lecture> lectures = lectureDao.findClassesByTeacherAndStatus(teacher.getId(), expectedLecture.getStatus(), pageRequest);
        final Page<Lecture> expectedLectures = InstanceProvider.getNewPage(Collections.singletonList(expectedLecture), pageRequest);

        Assert.assertEquals(1, lectures.getContent().size());
        Assert.assertEquals(expectedLectures, lectures);
    }

    @Test
    @Rollback
    public void testFindClassesByTeacherAndMultipleStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        PageRequest pageRequest = InstanceProvider.getNewPageRequest();
        final Page<Lecture> lectures = lectureDao.findClassesByTeacherAndMultipleStatus(teacher.getId(), expectedLecture.getStatus(), pageRequest);
        final Page<Lecture> expectedLectures = InstanceProvider.getNewPage(Collections.singletonList(expectedLecture), pageRequest);

        Assert.assertEquals(1, lectures.getContent().size());
        Assert.assertEquals(expectedLectures, lectures);
    }

    @Test
    @Rollback
    public void testCreate() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        lectureDao.create(student.getId(), teacher.getId(), expectedLecture.getLevel(), subject.getSubjectId(), expectedLecture.getPrice());
        entityManager.flush();

        Assert.assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"classes"));
    }

    @Test
    @Rollback
    public void testSetStatus() {
        final Lecture expectedLecture = InstanceProvider.getNewStatusLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final int rowsModified = lectureDao.setStatus(expectedLecture.getClassId(), 2);

        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "classes",
                "status = " + 2));
        Assert.assertEquals(1, rowsModified);
    }

    @Test
    @Rollback
    public void testGetNotificationsCount() {
        final Lecture expectedLecture = InstanceProvider.getNewTimeLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);
        entityManager.persist(InstanceProvider.getNewPost(student, expectedLecture));

        final int notifications = lectureDao.getNotificationsCount(expectedLecture.getClassId(), 0);

        Assert.assertEquals(1, notifications);
    }

    @Test
    @Rollback
    public void testRefreshTime() {
        final Lecture expectedLecture = InstanceProvider.getNewTimeLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final int rowsModified = lectureDao.refreshTime(expectedLecture.getClassId(), 0);
        Assert.assertEquals(1, rowsModified);
    }

    @Test
    @Rollback
    public void testAddSharedFileToLecture() {
        final SubjectFile subjectFile = InstanceProvider.getNewSubjectFile(teachesInfo);
        entityManager.persist(subjectFile);
        final Lecture expectedLecture = InstanceProvider.getNewLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);

        final int ret = lectureDao.addSharedFileToLecture(subjectFile.getFileId(), expectedLecture.getClassId());

        Assert.assertEquals(1, ret);
    }

    @Test
    @Rollback
    public void testStopSharingFileInLecture() {
        final SubjectFile subjectFile = InstanceProvider.getNewSubjectFile(teachesInfo);
        entityManager.persist(subjectFile);
        final Lecture expectedLecture = InstanceProvider.getNewLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);
        expectedLecture.getSharedFilesByTeacher().add(subjectFile);

        final int ret = lectureDao.stopSharingFileInLecture(subjectFile.getFileId(), expectedLecture.getClassId());

        Assert.assertEquals(1, ret);
    }

    @Test
    @Rollback
    public void testGetSharedFilesByTeacher() {
        final SubjectFile subjectFile = InstanceProvider.getNewSubjectFile(teachesInfo);
        entityManager.persist(subjectFile);
        final Lecture expectedLecture = InstanceProvider.getNewLecture(teacher, student, subject);
        entityManager.persist(expectedLecture);
        expectedLecture.getSharedFilesByTeacher().add(subjectFile);

        final List<SubjectFile> subjectFiles = lectureDao.getSharedFilesByTeacher(expectedLecture.getClassId());
        final List<SubjectFile> expectedSubjectFiles = new ArrayList<>();
        expectedSubjectFiles.add(subjectFile);

        Assert.assertEquals(1, subjectFiles.size());
        Assert.assertEquals(expectedSubjectFiles, subjectFiles);
    }

}
