package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = {"/user-test.sql"})
public class UserDaoJdbcTest {

    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USER_PASS = "1234";
    private static final int USER_ID = 1;

    @Autowired
    private UserDaoJdbc userDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate(){

        final User newUser = userDao.create(USERNAME,USER_MAIL);

        Assert.assertNotNull(newUser);
        Assert.assertEquals(USER_MAIL,newUser.getMail());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate,"users"));

    }

    @Test
    public void testAlreadyRegistered(){
        //TODO
    }

    @Test
    public void testFindById(){
        //TODO
    }
}
