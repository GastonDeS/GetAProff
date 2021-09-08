package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TeachesDao;
import ar.edu.itba.paw.models.Teaches;
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
public class TeachesDaoJdbc implements TeachesDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Teaches> ROW_MAPPER = (rs, rowNum) ->
            new Teaches(rs.getInt("userId"), rs.getInt("subjectId"),
                    rs.getInt("price"));

    @Autowired
    TeachesDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("subject");
    }

    @Override
    public Teaches addSubjectToUser(int userid, int subjectid, int price) {
        final Map<String, Object> args = new HashMap<>();
        args.put("userid", userid);
        args.put("subjectid",subjectid);
        args.put("price",price);
        final Number userId = jdbcInsert.execute(args);
        return new Teaches(userid, subjectid, price);
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
