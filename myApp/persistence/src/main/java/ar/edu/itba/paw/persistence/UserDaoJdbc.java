package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Timetable;
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

@Repository
public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getString("name"), rs.getString("password"),
            rs.getInt("userid"), rs.getString("mail"));

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
    public User create(String name, String mail) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("password",null);
        args.put("mail",mail);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(name, null, userId.intValue(), mail);
    }

    @Override
    public List<CardProfile> findUsersBySubjectId(int subjectId) {
        List<String> subjectName = jdbcTemplate.queryForList("SELECT name FROM subject WHERE subjectId = ?", new Object[] {subjectId}, String.class);
        return subjectName.isEmpty() ? null : findUsersBySubject(subjectName.get(0));
    }

    @Override
    public List<CardProfile> findUsersBySubject(String subject) {
        RowMapper<CardProfile> mapper = (rs, rowNum) -> new CardProfile(rs.getInt("userId"), rs.getString("name"),
                rs.getString("subject"), rs.getInt("price"), new String[] { rs.getString("monday"),
                rs.getString("tuesday"), rs.getString("wednesday"), rs.getString("thursday"),
                rs.getString("friday"), rs.getString("saturday"), rs.getString("sunday")});

        String query = "SELECT aux.userid, aux.name AS name, s.name AS subject, price, t.monday AS monday, t.tuesday AS tuesday,\n" +
                "                t.wednesday AS wednesday, t.thursday AS thursday, t.friday AS friday, t.saturday AS saturday, t.sunday AS sunday\n" +
                "                FROM (SELECT subjectid, u.userid, price, name FROM teaches t JOIN users u on u.userid = t.userid) AS aux\n" +
                "                JOIN subject s ON aux.subjectid = s.subjectid JOIN timetable t ON t.userid = aux.userid WHERE lower(s.name) SIMILAR TO '%'||?||'%'";
        List<CardProfile> list = jdbcTemplate.query(
                query, new Object[] {subject.toLowerCase()}, mapper);
        return list.isEmpty() ? null : list;
    }
}
