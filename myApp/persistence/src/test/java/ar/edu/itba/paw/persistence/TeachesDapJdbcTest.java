package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
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
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TeachesDapJdbcTest {

//    @Autowired
//    private TeachesDaoJdbc teachesDao;
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
//        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
//        JdbcTestUtils.deleteFromTables(jdbcTemplate,"teaches");
//        jdbcTemplate.execute("insert into subject values (0,'matematica'),(1,'ingles')");
//
//    }
//
//    @Test
//    public void testGetSubjectInfoListByUser() {
//        jdbcTemplate.execute("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.execute("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into teaches values (0,0,1500,1)");
//        jdbcTemplate.update("insert into teaches values (0,1,2000,1)");
//
//
//        List<SubjectInfo> subjectInfos = teachesDao.getSubjectInfoListByUser(0L);
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
