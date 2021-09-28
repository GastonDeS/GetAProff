package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.models.Class;


import ar.edu.itba.paw.models.SubjectInfo;
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
public class ClassDaoJdbc implements ClassDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Class> ROW_MAPPER = (rs, rowNum) -> new Class(
            rs.getInt("classid"),
            new User(rs.getString("studentname"), rs.getString("studentpassword"), rs.getInt("studentid"), rs.getString("studentmail"), rs.getInt("studentrole")),
            new User(rs.getString("teachername"), rs.getString("teacherpassword"), rs.getInt("teacherid"), rs.getString("teachermail"), rs.getInt("teacherrole"), rs.getString("teacherdescription"), rs.getString("teacherschedule")),
            new SubjectInfo( rs.getInt("subjectid"),rs.getString("subjectname"), rs.getInt("price"), rs.getInt("level")),
            rs.getInt("status"));

    @Autowired
    public ClassDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("classes")
                .usingGeneratedKeyColumns("classid");
    }


    @Override
    public Class get(int id) {
        final List<Class> list = jdbcTemplate.query("SELECT * FROM front_classes WHERE classid = ?", new Object[] { id }, ROW_MAPPER);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Class> findClassesByStudentId(int id) {
        final List<Class> list = jdbcTemplate.query("SELECT * FROM front_classes WHERE studentid = ?", new Object[] { id }, ROW_MAPPER);
        return list;
    }

    @Override
    public List<Class> findClassesByTeacherId(int id) {
        final List<Class> list = jdbcTemplate.query("SELECT * FROM front_classes WHERE teacherid = ?", new Object[] { id }, ROW_MAPPER);
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
        final List<Class> list = jdbcTemplate.query("SELECT * FROM front_classes WHERE classid = ?", new Object[] { classId }, ROW_MAPPER);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int setStatus(int classId, int status) {
        return jdbcTemplate.update("UPDATE classes SET status = ? WHERE classid = ?", status, classId);
    }


}
