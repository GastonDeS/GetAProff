package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TeachesDao;
import ar.edu.itba.paw.models.Teaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Repository
public class TeachesDaoJdbc implements TeachesDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Teaches> ROW_MAPPER = (rs, rowNum) ->
            new Teaches(rs.getInt("userId"), rs.getInt("subjectId"),
                    rs.getInt("price"), rs.getString("timeInterval"));

    @Autowired
    TeachesDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS teaches ("
//                + "userId INTEGER,"
//                + "subjectId INTEGER,"
//                + "price INTEGER,"
//                + "timeInterval VARCHAR(100),"
//                + "FOREIGN KEY (userId) REFERENCES users,"
//                + "FOREIGN KEY (subjectId) REFERENCES subject,"
//                + "PRIMARY KEY (userId,subjectId,price)"
//                + ")");

        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("subject");
    }
    @Override
    public List<Teaches> findUserBySubject(int subjectId) {
        final List<Teaches> list = jdbcTemplate.query("SELECT * FROM teaches WHERE 'subjectId' = ?", new Object[] { subjectId },
                ROW_MAPPER);
        return list.isEmpty() ? null : list ;
    }

    @Override
    public List<Teaches> findSubjectByUser(int userId) {
        final List<Teaches> list = jdbcTemplate.query("SELECT * FROM teaches WHERE 'userId' = ?",  new Object[] { userId },
                ROW_MAPPER);
        return list.isEmpty() ? null : list ;
    }
}
