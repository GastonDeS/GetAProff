package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class SubjectDaoJdbc implements SubjectDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Subject> ROW_MAPPER = (rs, rowNum) ->
            new Subject(rs.getString("name"), rs.getInt("subjectId"));

    @Autowired
    SubjectDaoJdbc(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS subject ("
                + "subjectId INTEGER PRIMARY KEY,"
                + "name TEXT"
                + ")");

        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("subject")
                .usingGeneratedKeyColumns("subjectId");
    }

    @Override
    public Optional<Subject> findById(int id) {
        return jdbcTemplate.query("SELECT * FROM subject WHERE 'subjectId' = ?", new Object[] { id }, ROW_MAPPER)
                .stream().findFirst();
    }
}
