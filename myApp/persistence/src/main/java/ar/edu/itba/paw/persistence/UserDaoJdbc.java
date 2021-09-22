package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getString("name"), rs.getString("password"),
            rs.getInt("userid"), rs.getString("mail"), rs.getInt("userRole"));

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
        RowMapper<CardProfile> mapper = (rs, rowNum) -> new CardProfile(rs.getInt("userId"), rs.getString("name"),
                rs.getString("subject"), rs.getInt("price"),rs.getInt("level"), new String[] { rs.getString("monday"),
                rs.getString("tuesday"), rs.getString("wednesday"), rs.getString("thursday"),
                rs.getString("friday"), rs.getString("saturday"), rs.getString("sunday")}, rs.getString("description"));

        int minLevel, maxLevel;
        if( level == 0) { minLevel = 1; maxLevel = 3;}
        else
            minLevel = maxLevel = level;

        String query = "SELECT aux.userid, aux.name AS name, s.name AS subject, price, level, t.monday AS monday, t.tuesday AS tuesday,\n" +
                "                t.wednesday AS wednesday, t.thursday AS thursday, t.friday AS friday, t.saturday AS saturday, t.sunday AS sunday, description\n" +
                "                FROM (SELECT subjectid, u.userid, price, name, level, description FROM teaches t JOIN users u on u.userid = t.userid) AS aux\n" +
                "                JOIN subject s ON aux.subjectid = s.subjectid JOIN timetable t ON t.userid = aux.userid WHERE lower(s.name) SIMILAR TO '%'||?||'%' " +
                "                AND price <= ? AND level IN (?,?) ";
        List<CardProfile> list = jdbcTemplate.query(
                query, new Object[] {subject.toLowerCase(),price, minLevel, maxLevel }, mapper);
        return list.isEmpty() ? null : list;
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        return jdbcTemplate.query("SELECT * FROM users WHERE mail = ?", new Object[] {mail}, ROW_MAPPER)
                .stream().findFirst();
    }
}
