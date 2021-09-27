package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.models.Timetable;
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
            rs.getInt("userid"), rs.getString("mail"), rs.getInt("userRole"), rs.getString("description")
    ,rs.getString("schedule"));

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
    public User create(String name, String mail, String password, int userRole) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("password",password);
        args.put("mail",mail);
        args.put("userRole", userRole);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(name, password, userId.intValue(), mail, userRole);
    }

    @Override
    public List<CardProfile> findUsersBySubjectId(int subjectId) {
        List<String> subjectName = jdbcTemplate.queryForList("SELECT name FROM subject WHERE subjectId = ?", new Object[] {subjectId}, String.class);
        return subjectName.isEmpty() ? null : filterUsers(subjectName.get(0),Integer.MAX_VALUE,0);
    }

    @Override
    public List<CardProfile> filterUsers(String subject, Integer price, Integer level) {
        RowMapper<CardProfile> mapper = (rs, rowNum) -> new CardProfile(rs.getInt("userId"), rs.getString("name"), rs.getInt("maxPrice"),rs.getInt("minPrice"), rs.getString("description"));

        int minLevel, maxLevel;
        if( level == 0) { minLevel = 1; maxLevel = 3;}
        else
            minLevel = maxLevel = level;

        String query = "SELECT aux.userid, aux.name AS name, max(price) as maxPrice, min(price) as minPrice, description\n" +
                "                FROM (SELECT subjectid, u.userid, price, name, level, description FROM teaches t JOIN users u on u.userid = t.userid) AS aux\n" +
                "                JOIN subject s ON aux.subjectid = s.subjectid  WHERE lower(s.name) SIMILAR TO '%'||?||'%' " +
                "                AND price <= ? AND ( level BETWEEN ? AND ? OR level = 0 ) GROUP BY aux.userid, aux.name, aux.description";
        List<CardProfile> list = jdbcTemplate.query(
                query, new Object[] {subject.toLowerCase().trim(),price, minLevel, maxLevel }, mapper);
        return list.isEmpty() ? null : list;
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
}
