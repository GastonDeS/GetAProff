package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
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
        jdbcTemplate.execute("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");

        final User register2 = userDao.create("gaston","gdeschant@itba.edu.ar","GFDA23faS$#","the best teacher ever","every day since 8 am to 4 pm");

        Assert.fail("Duplicate key");
    }

    @Test
    public void testSetUserSchedule(){
        final int valid = userDao.setUserSchedule(10,"todos los dias my rey");

        Assert.assertEquals(0,valid);
    }

    @Test
    public void testGetRatingById() {
        jdbcTemplate.execute("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.execute("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.execute("insert into rating values (0,1,4.5,'the teacher on my life')");

        final Pair<Float,Integer> ratingById = userDao.getRatingById(0);

        Assert.assertEquals(Float.valueOf(4.5f), ratingById.getValue1());
        Assert.assertEquals(Integer.valueOf(1), ratingById.getValue2());
    }

    @Test
    public void testFilterUsers() {
        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into userRoles values (0,1)");
        jdbcTemplate.update("insert into favourites values (1,0)");
        jdbcTemplate.update("insert into subject values (0,'matematica'),(1,'ingles')");
        jdbcTemplate.update("insert into teaches values (1,0,1500,1)");


        List<CardProfile> cardProfileList = userDao.filterUsers("mate",1,1500,1,0,0);

        Assert.assertFalse(cardProfileList.isEmpty());
        Assert.assertEquals("naso",cardProfileList.get(0).getName());
    }

    @Test
    public void testGetFavourites() {
        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into userRoles values (0,1)");
        jdbcTemplate.update("insert into favourites values (1,0)");
        jdbcTemplate.update("insert into subject values (0,'matematica'),(1,'ingles')");
        jdbcTemplate.update("insert into teaches values (1,0,1500,1)");


        List<CardProfile> cardProfileList = userDao.getFavourites(0);

        Assert.assertFalse(cardProfileList.isEmpty());
        Assert.assertEquals("naso",cardProfileList.get(0).getName());
    }

    @Test
    public void testRemoveFavourite() {
        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into favourites values (0,1)");

        int removed = userDao.removeFavourite(0,1);

        Assert.assertEquals(1,removed);
    }

    @Test
    public void testGet() {
        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");

        Optional<User> user = userDao.get(0);

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals("gaston",user.get().getName());

    }

    @Test
    public void testGetPageQty() {
        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into users values (1,'naso','GFDA23faS$#','anaso@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
        jdbcTemplate.update("insert into userRoles values (0,1)");
        jdbcTemplate.update("insert into favourites values (1,0)");
        jdbcTemplate.update("insert into subject values (0,'matematica'),(1,'ingles')");
        jdbcTemplate.update("insert into teaches values (1,0,1500,1)");

        Integer pages = userDao.getPageQty("mate",0,0,0);

        Assert.assertEquals(Integer.valueOf(pages),pages);

    }

}
