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

@Repository
public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getString("name"), rs.getString("password"),
            rs.getInt("userid"), rs.getString("mail"));

    @Autowired
    public UserDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users ("
                + "userid SERIAL PRIMARY KEY,"
                + "name varchar(100),"
                + "password varchar(100),"
                + "mail varchar(100)"
                + ")");

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
    public User create(String name) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name); // la key es el nombre de la columna
        args.put("password","pass");
        args.put("mail","@.com");
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(name, "pass", userId.intValue(), "@.com");
    }

    @Override
    public List<CardProfile> findUsersBySubject(int subjectId) {
        RowMapper<CardProfile> mapper = (rs, rowNum) -> new CardProfile(rs.getInt("userId"), rs.getString("name"),
                rs.getString("subject"), rs.getInt("price"));
        List<CardProfile> list = jdbcTemplate.query(
                "SELECT userid, aux.name AS name, s.name AS subject, price\n" +
                "FROM (SELECT subjectid, u.userid, price, name FROM teaches t JOIN users u on u.userid = t.userid) AS aux\n" +
                "JOIN subject s ON aux.subjectid = s.subjectid\n" +
                "WHERE s.subjectID = ?", new Object[] {subjectId}, mapper);
        return list.isEmpty() ? null : list;
    }
}
