package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class TeachesDaoJpaTest {

    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USER_PASS = "1234";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final String SCHEDULE = "todos los dias habiles de 8 a 16";
    private static final Long USER_ID_ONE = 1L;
    private static final Long USER_ID_TWO = 2L;
    private static final String SUBJECT_ONE = "MATE 1";
    private static final String SUBJECT_TWO = "MATE 2";
    private static final Long SUBJECT_ID_ONE = 1L;
    private static final Long SUBJECT_ID_TWO = 2L;
    private static final Integer PRICE = 500;
    private static final Integer LEVEL = 3;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final float RATE = 3.99f ;
    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0, MAX_LEVEL = 3, GET_ALL = 0, OVER_PRICE = 1500;

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
        user = new User(USERNAME, USER_PASS, null, USER_MAIL, DESCRIPTION, SCHEDULE);
        entityManager.persist(user);
        subject = new Subject(SUBJECT_ONE, null);
        entityManager.persist(subject);
        teaches = new Teaches(user, subject, PRICE, LEVEL);
    }

    @Test
    @Rollback
    public void testAddSubjectToUser() {
        Teaches maybeTeaches = teachesDao.addSubjectToUser(USER_ID_ONE, SUBJECT_ID_ONE, PRICE, LEVEL);

        Assert.assertNotNull(maybeTeaches);
        Assert.assertEquals(teaches, maybeTeaches);
    }

//    @Test
//    public void testGetSubjectInfoListByUser() {
//        jdbcTemplate.execute("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.execute("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into teaches values (0,0,1500,1)");
//        jdbcTemplate.update("insert into teaches values (0,1,2000,1)");
//
//
//        List<Object> subjectInfoRaw = teachesDao.getSubjectInfoListByUser(0L);
//
//        subjectInfos = subjectInfos.stream().sorted((p,m) -> (p.getId() - m.getId())).collect(Collectors.toList());
//
//        Assert.assertEquals(2,subjectInfos.size());
//        Assert.assertEquals(0,subjectInfos.get(0).getId());
//        Assert.assertEquals(1,subjectInfos.get(1).getId());
//    }
//
//    @Test
//    public void testAddSubjectToUser() {
//        jdbcTemplate.execute("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//
//
//        Teaches teaches = teachesDao.addSubjectToUser(0L,0L,500,0);
//
//        Assert.assertEquals(Long.valueOf(0), teaches.getUserId());
//
//    }

}
