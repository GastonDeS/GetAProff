package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.models.Subject;
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
public class SubjectDaoJdbc implements SubjectDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Subject> ROW_MAPPER = (rs, rowNum) ->
            new Subject(rs.getString("name"), rs.getInt("subjectid"));

    @Autowired
    public SubjectDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("subject")
                .usingGeneratedKeyColumns("subjectid");
    }

    @Override
    public Optional<Subject> findById(int id) {
        return jdbcTemplate.query("SELECT * FROM subject WHERE subjectId = ?", new Object[] { id }, ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<Subject> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM subject WHERE lower(name) LIKE lower(?)",
                        new Object[] { name }, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Subject save(Subject subject) {
        return null;
    }

    @Override
    public Subject create (String subject) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", subject);
        final Number subjectid = jdbcInsert.executeAndReturnKey(args);
        return new Subject(subject, subjectid.intValue());
    }

    @Override
    public List<Subject> listSubjects() {
        final List<Subject> list = jdbcTemplate.query("SELECT * FROM subject", ROW_MAPPER);
        return list;
    }

    @Override
    public List<Subject> subjectsNotGiven(int userId) {
        final List<Subject> list = jdbcTemplate.query(
                "SELECT * FROM subject WHERE subjectid NOT IN " +
                        "(SELECT subjectid FROM teaches WHERE userid = ?) ",
                new Object[] {userId}, ROW_MAPPER);
        return list;
     }
}
