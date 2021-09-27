package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.models.Class;

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
public class ClassDaoJdbc implements ClassDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Class> ROW_MAPPER = (rs, rowNum) -> new Class(rs.getInt("classid"), rs.getInt("studentId"),
            rs.getInt("teacherId"), rs.getInt("level"), rs.getInt("subjectId"), rs.getInt("price") ,rs.getInt("status"));

    @Autowired
    public ClassDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("classes")
                .usingGeneratedKeyColumns("classid");
    }


    @Override
    public Class get(int id) {
        final List<Class> list = jdbcTemplate.query("SELECT * FROM classes WHERE classid = ?", new Object[] { id }, ROW_MAPPER);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Class> findClassesByStudentId(int id) {
        final List<Class> list = jdbcTemplate.query("SELECT * FROM classes WHERE studentid = ?", new Object[] { id }, ROW_MAPPER);
        return list;
    }

    @Override
    public List<Class> findClassesByTeacherId(int id) {
        final List<Class> list = jdbcTemplate.query("SELECT * FROM classes WHERE teacherid = ?", new Object[] { id }, ROW_MAPPER);
        return list;
    }

    @Override
    public Class create(int studentId, int teacherId, int level, int subjectId, int price, int status) {
        final Map<String, Object> args = new HashMap<>();
        args.put("studentid", studentId);
        args.put("teacherid", teacherId);
        args.put("level", level);
        args.put("subjectid", subjectId);
        args.put("price", price);
        args.put("status", status);
        final Number classId = jdbcInsert.executeAndReturnKey(args);
        return new Class(classId.intValue(), studentId, teacherId, level, subjectId, price, status);
    }

    @Override
    public int setStatus(int classId, int status) {
        return jdbcTemplate.update("UPDATE classes SET status = ? WHERE classid = ?", status, classId);
    }


}
