package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
//@Sql(scripts = "classpath:user-test.sql")
public class UserDaoJdbcTest {

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

    @Autowired
    private UserDaoJdbc userDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"roles");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"rating");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"favourites");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"teaches");
    }

    @Test
    public void testCreate(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");

        final User register = userDao.create(USERNAME,USER_MAIL,USER_PASS,DESCRIPTION,SCHEDULE);

        Assert.assertNotNull(register);
        Assert.assertEquals(USER_MAIL, register.getMail());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"users","name = '"+USERNAME+"'"));
    }

    @Test(expected = DuplicateKeyException.class)
    public void testAlreadyRegistered(){
        final User register = userDao.create("gaston","gdeschant@itba.edu.ar","GFDA23faS$#","the best teacher ever","every day since 8 am to 4 pm");
        final User register2 = userDao.create("gaston","gdeschant@itba.edu.ar","GFDA23faS$#","the best teacher ever","every day since 8 am to 4 pm");

        Assert.fail("Duplicate key");
    }

    @Test
    public void testSetUserSchedule(){
        final int valid = userDao.setUserSchedule(10,"todos los dias my rey");

        Assert.assertEquals(0,valid);
    }



}
