package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeachesDaoJdbc implements TeachesDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Teaches> ROW_MAPPER = (rs, rowNum) ->
            new Teaches(rs.getInt("userId"), rs.getInt("subjectId"),
                    rs.getInt("price"), rs.getInt("level"));

    @Autowired
    TeachesDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("teaches");
    }

    @Override
    public Teaches addSubjectToUser(int userid, int subjectid, int price, int level) {
        final Map<String, Object> args = new HashMap<>();
        args.put("userid", userid);
        args.put("subjectid", subjectid);
        args.put("price", price);
        args.put("level", level);
        jdbcInsert.execute(args);
        return new Teaches(userid, subjectid, price, level);
    }

    @Override
    public List<Teaches> findUserBySubject(int subjectId) {
        final List<Teaches> list = jdbcTemplate.query("SELECT * FROM teaches WHERE subjectId = ?",
                new Object[] { subjectId }, ROW_MAPPER);
        return list.isEmpty() ? null : list ;
    }

    @Override
    public List<Teaches> findSubjectByUser(int userId) {
        final List<Teaches> list = jdbcTemplate.query("SELECT * FROM teaches WHERE userId = ?",
                new Object[] { userId }, ROW_MAPPER);
        return list.isEmpty() ? new ArrayList<>() : list ;
    }

    @Override
    public Teaches findByUserAndSubject(int userId, int subjectId) {
        final List<Teaches> list = jdbcTemplate.query(
                "SELECT * FROM teaches WHERE userId = ? AND subjectId = ?", new Object[] {userId, subjectId}, ROW_MAPPER);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int removeSubjectToUser(int userId, int subjectId) {
        return jdbcTemplate.update("DELETE FROM teaches WHERE userId = ? AND subjectId = ?", userId, subjectId);
        // Returns != 0 if removed correctly
    }

    @Override
    public List<SubjectInfo> getSubjectInfoListByUser(int userid) {
        RowMapper<SubjectInfo> subjectInfoRowMapper = (rs, rowNum) ->
                new SubjectInfo(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("price"), rs.getInt("level"));
        List<SubjectInfo> list = jdbcTemplate.query(
                "SELECT s.subjectid as id, s.name as name, price, level\n" +
                        "FROM teaches t JOIN subject s ON t.subjectid = s.subjectid WHERE userId = ?",
                new Object[] {userid}, subjectInfoRowMapper);
        return list.isEmpty() ? null : list;
    }

}
