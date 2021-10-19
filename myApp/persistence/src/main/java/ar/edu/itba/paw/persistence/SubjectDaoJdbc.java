package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class SubjectDaoJdbc implements SubjectDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Subject> ROW_MAPPER = (rs, rowNum) ->
            new Subject(rs.getString("name"), rs.getLong("subjectid"));

    @Autowired
    public SubjectDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("subject")
                .usingGeneratedKeyColumns("subjectid");
    }

    @Override
    public Optional<Subject> findById(Long id) {
        List<Subject> list = jdbcTemplate.query("SELECT * FROM subject WHERE subjectId = ?", new Object[] { id }, ROW_MAPPER);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public Optional<Subject> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM subject WHERE lower(name) LIKE lower(?)",
                        new Object[] { name }, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Subject create (String subject) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", subject);
        final Number subjectid = jdbcInsert.executeAndReturnKey(args);
        return new Subject(subject, (long) subjectid.intValue());
    }

    @Override
    public List<Subject> listSubjects() {
        final List<Subject> list = jdbcTemplate.query("SELECT * FROM subject", ROW_MAPPER);
        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
