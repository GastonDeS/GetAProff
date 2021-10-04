package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getString("name"), rs.getString("password"),
            rs.getInt("userid"), rs.getString("mail"), rs.getString("description"),rs.getString("schedule"));

    @Autowired
    public UserDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("userid");
    }

    @Override
    public User get(int id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", new Object[] { id }, ROW_MAPPER);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<User> list() {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users", ROW_MAPPER);
        return list.isEmpty() ? null : list;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User create(String username, String mail, String password, String description, String schedule) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", username);
        args.put("password",password);
        args.put("mail",mail);
        args.put("description", description);
        args.put("schedule", schedule);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(username, password, userId.intValue(), mail, description, schedule);
    }

    @Override
    public List<CardProfile> findUsersBySubjectId(int subjectId) {
        List<String> subjectName = jdbcTemplate.queryForList("SELECT name FROM subject WHERE subjectId = ?", new Object[] {subjectId}, String.class);
        return subjectName.isEmpty() ? null : filterUsers(subjectName.get(0),Integer.MAX_VALUE,0);
    }

    @Override
    public List<CardProfile> filterUsers(String subject, Integer price, Integer level) {
        RowMapper<CardProfile> mapper = (rs, rowNum) -> new CardProfile(rs.getInt("userId"), rs.getString("name"),
                rs.getInt("maxPrice"),rs.getInt("minPrice"), rs.getString("description"),
                rs.getInt("image"), rs.getFloat("rate"));
        int minLevel, maxLevel;
        if( level == 0) { minLevel = 1; maxLevel = 3;}
        else
            minLevel = maxLevel = level;
        String query = "select * from (select a4.userid,name,maxPrice,minPrice,description,image ,sum(coalesce(rate,0))/count(coalesce(rate,0)) as rate\n" +
                        "from (SELECT a3.uid as userid, a3.name as name, maxPrice, minPrice, description, image\n" +
                        "FROM ((SELECT uid, name, description, image, max(price) as maxPrice, min(price) as minPrice\n" +
                        "FROM (SELECT u.userid AS uid, name, description, (CASE WHEN image IS NULL THEN 0 ELSE 1 END) AS image\n" +
                        "FROM images RIGHT OUTER JOIN users u on u.userid = images.userid) AS a1 JOIN teaches t ON a1.uid = t.userid\n" +
                        "GROUP BY uid, name, description, image) AS a2 JOIN teaches t ON a2.uid = t.userid) AS a3\n"+
                        "JOIN subject s ON a3.subjectid = s.subjectid\n" +
                        "WHERE lower(s.name) SIMILAR TO '%'||?||'%' AND price <= ? AND ( level BETWEEN ? AND ? OR level = 0)) as a4 LEFT OUTER JOIN rating r ON a4.userid = teacherid\n" +
                        "group by a4.userid, name, maxPrice, minPrice, description, image) as a5\n" +
                        "group by a5.userid, name, maxPrice, minPrice, description, image, rate\n" +
                        "ORDER BY rate DESC";
        List<CardProfile> list = jdbcTemplate.query(
                query, new Object[] {subject.toLowerCase().trim(),price, minLevel, maxLevel }, mapper);
        return list.isEmpty() ? null : list;
    }

    @Override
    public List<CardProfile> getFavourites(int uid) {
        RowMapper<CardProfile> mapper = (rs,rowNum) -> new CardProfile(rs.getInt("userId"), rs.getString("name"),
                rs.getInt("maxPrice"),rs.getInt("minPrice"), rs.getString("description"),
                rs.getInt("image"), rs.getFloat("rate"));
        String query = "select teacherid as userId, name,description, maxprice, minprice, image, rate from\n" +
                "    (select rating.teacherid as teacherid, name,description,maxprice, minprice, image, sum(coalesce(rate,0))/count(coalesce(rate,0)) as rate from\n" +
                "  (select teacherid,name,description,maxprice, minprice,(CASE WHEN image IS NULL THEN 0 ELSE 1 END) AS image\n" +
                "    from (select teacherid, name, description, max(price) as maxprice, min(price) as minprice from\n" +
                "      (select teacherid,price from\n" +
                "     (select teacherid from favourites where studentid = ?) as a1\n" +
                "         join teaches t on t.userid = a1.teacherid) as a2 join users u on teacherid = userid\n" +
                "        group by teacherid, name,description) as a3 left outer join images on a3.teacherid = images.userid)\n" +
                "        as a4 join rating on a4.teacherid = rating.teacherid\n" +
                "        group by rating.teacherid, name, description, maxprice, minprice, image) as a5\n" +
                "group by teacherid, name,description, maxprice, minprice, image, rate\n" +
                "order by rate DESC";
        List<CardProfile> list = jdbcTemplate.query(
                query, new Object[] {uid}, mapper);
        return list().isEmpty()? null : list;
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        return jdbcTemplate.query("SELECT * FROM users WHERE mail = ?", new Object[] {mail}, ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Map<Integer, List<String>> getUserSubjectsAndLevels(int userId) {
        RowMapper<Pair<Integer,String>> pairRowMapper = (rs, rowNum) -> new Pair<>(rs.getInt("level"),rs.getString("name"));
        List<Pair<Integer,String>> rSet = jdbcTemplate.query("SELECT level, name FROM TEACHES JOIN subject s on teaches.subjectid = s.subjectid WHERE userid = ?",
                new Object[] { userId}, pairRowMapper);
        Map<Integer,List<String>> map = new HashMap<>();
        for(Pair<Integer,String> p : rSet){
            map.computeIfAbsent(p.getValue1(), k -> new ArrayList<>());
            map.get(p.getValue1()).add(p.getValue2());
        }
        return map;
    }

    @Override
    public int setUserSchedule(int userId, String schedule) {
        return jdbcTemplate.update("UPDATE users SET schedule = ? WHERE userid = ?", schedule, userId);
    }

    @Override
    public int setUserDescription(int userId, String description) {
        return jdbcTemplate.update("UPDATE users SET description = ? WHERE userid = ?", description, userId);
    }

    @Override
    public boolean isFaved(int teacherId, int studentId) {
        RowMapper<String> pairRowMapper = (rs, rowNum) -> (String.valueOf(rs.getInt("count")));
        return jdbcTemplate.query("select count(*) as count\n" +
                "from favourites\n" +
                "where teacherid = ?\n" +
                "  and  studentid = ?;", new Object[]{teacherId,studentId},pairRowMapper).get(0).equals("1");
    }

    @Override
    public int addFavourite(int teacherId, int studentId) {
        return jdbcTemplate.update("insert into favourites\n" +
                "values (?,?)\n" +
                "on conflict do nothing;",teacherId,studentId);
    }

    @Override
    public int removeFavourite(int teacherId, int studentId) {
        return jdbcTemplate.update("delete from favourites\n" +
                "where teacherid = ? AND studentid = ?;",teacherId,studentId);
    }
}
