package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RoleDaoJdbcTest {

//    @Autowired
//    private RoleDaoJdbc roleDao;
//
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp(){
//        jdbcTemplate = new JdbcTemplate(ds);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
//        JdbcTestUtils.deleteFromTables(jdbcTemplate,"userRoles");
//    }
//
//    @Test
//    public void testGetUserRoles() {
//        jdbcTemplate.execute("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,0)");
//
//        List<Role> roles =  roleDao.getUserRoles(0L);
//
//        Assert.assertFalse(roles.isEmpty());
//        Assert.assertEquals(1,roles.size());
//        Assert.assertEquals(Long.valueOf(1),roles.get(0).getRoleId());
//    }


}
