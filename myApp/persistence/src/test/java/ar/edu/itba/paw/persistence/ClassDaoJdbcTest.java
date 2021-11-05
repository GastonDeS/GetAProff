package ar.edu.itba.paw.persistence;

//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class ClassDaoJdbcTest {
//
//    @Autowired
//    private LectureDaoJdbc classDao;
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
//        JdbcTestUtils.deleteFromTables(jdbcTemplate,"subject");
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "classes");
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teaches");
//
//    }
//
//    @Test
//    public void testCreate() {
//        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,0)");
//        jdbcTemplate.update("insert into users values (1,'segun','GFDA23faS$#','sespina@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,1)");
//        jdbcTemplate.update("insert into subject values (1,'matematica')");
//        jdbcTemplate.update("insert into teaches values (0,1,200,2)");
//
//        final Lecture myClass = classDao.create(1L, 0L,2, 1L, 200, 0, "hola");
//        Assert.assertNotNull(myClass);
//        Assert.assertEquals(Long.valueOf(1), myClass.getStudentId());
//    }
//
//    @Test
//    public void testFindClassesByTeacherId() {
//        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,0)");
//        jdbcTemplate.update("insert into users values (1,'segun','GFDA23faS$#','sespina@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,1)");
//        jdbcTemplate.update("insert into subject values (0,'matematica')");
//        jdbcTemplate.update("insert into teaches values (0,0,200,2)");
//        jdbcTemplate.update("insert into classes values (0,1,0,2,0,200,0,0,'hola','')");
//        jdbcTemplate.update("insert into classes values (1,1,0,2,0,200,0,0,'hola','')");
//
//        final List<ClassInfo> myClasses = classDao.findClassesByTeacherId((long) 0);
//        Assert.assertNotNull(myClasses);
//        Assert.assertEquals(2, myClasses.size());
//    }
//    @Test
//    public void testEmptyFindClassesByTeacherId() {
//        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,0)");
//        jdbcTemplate.update("insert into users values (1,'segun','GFDA23faS$#','sespina@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,1)");
//        jdbcTemplate.update("insert into subject values (0,'matematica')");
//        jdbcTemplate.update("insert into teaches values (0,0,200,2)");
//
//        final List<ClassInfo> myClasses = classDao.findClassesByTeacherId((long) 0);
//        Assert.assertEquals(0, myClasses.size());
//
//    }
//
//    @Test
//    public void testFindClassesByStudentId() {
//        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,0)");
//        jdbcTemplate.update("insert into users values (1,'segun','GFDA23faS$#','sespina@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,1)");
//        jdbcTemplate.update("insert into subject values (0,'matematica')");
//        jdbcTemplate.update("insert into teaches values (0,0,200,2)");
//        jdbcTemplate.update("insert into classes values (0,1,0,2,0,200,0,0,'hola','')");
//        jdbcTemplate.update("insert into classes values (1,1,0,2,0,200,0,0,'hola','')");
//
//        final List<ClassInfo> myClasses = classDao.findClassesByStudentId((long) 1);
//        Assert.assertNotNull(myClasses);
//        Assert.assertEquals(2, myClasses.size());
//    }
//    @Test
//    public void testEmptyFindClassesByStudentId() {
//        jdbcTemplate.update("insert into users values (0,'gaston','GFDA23faS$#','gdeschant@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,0)");
//        jdbcTemplate.update("insert into users values (1,'segun','GFDA23faS$#','sespina@itba.edu.ar','the best teacher ever','every day since 8 am to 4 pm')");
//        jdbcTemplate.update("insert into userRoles values (1,1)");
//        jdbcTemplate.update("insert into subject values (0,'matematica')");
//        jdbcTemplate.update("insert into teaches values (0,0,200,2)");
//
//        final List<ClassInfo> myClasses = classDao.findClassesByStudentId((long) 1);
//        Assert.assertEquals(0, myClasses.size());
//
//    }
//}
