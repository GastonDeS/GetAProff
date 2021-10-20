package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ClassDao;

import ar.edu.itba.paw.models.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

public class ClassDaoJdbc  {
//
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert jdbcInsert;
//    private final static RowMapper<Class> ROW_MAPPER = (rs, rowNum) -> new Class(
//            rs.getLong("classid"), rs.getLong("studentid"), rs.getLong("teacherid"), rs.getInt("level"),
//            rs.getLong("subjectid"), rs.getInt("price"), rs.getInt("status"), rs.getString("request"), rs.getString("reply"), rs.getInt("deleted"));
//    private final static RowMapper<ClassInfo> CLASS_INFO_ROW_MAPPER = (rs, rowNum) -> new ClassInfo(
//            rs.getString("teacherName"), rs.getString("studentName"), rs.getString("subjectName"), rs.getString("reply"),
//            rs.getString("request"), rs.getInt("price"), rs.getInt("level"), rs.getInt("status"), rs.getInt("classId"), rs.getInt("deleted"));
//
//    @Autowired
//    public ClassDaoJdbc(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("classes")
//                .usingGeneratedKeyColumns("classid");
//    }
//
//    @Override
//    public Optional<Class> get(Long id) {
//        final List<Class> list = jdbcTemplate.query("SELECT * FROM classes WHERE classid = ?", new Object[]{id}, ROW_MAPPER);
//        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
//    }
//
//    @Override
//    public List<ClassInfo> findClassesByStudentId(Long id) {
//        String query = "SELECT classId,  st.name AS studentName, t.name AS teacherName, level, status,\n" +
//                "       price, s.name AS subjectName, a1.request AS request, a1.reply AS reply, a1.deleted AS deleted\n" +
//                "FROM (SELECT * FROM classes WHERE studentid = ?) AS a1 JOIN users st ON st.userId = a1.studentId\n" +
//                "             JOIN users t ON t.userid = a1.teacherid JOIN subject s on a1.subjectid = s.subjectid";
//        List<ClassInfo> list = jdbcTemplate.query(query, new Object[] { id }, CLASS_INFO_ROW_MAPPER);
//        return list.isEmpty() ? new ArrayList<>() : list;
//    }
//
//    @Override
//    public List<ClassInfo> findClassesByTeacherId(Long id) {
//        String query = "SELECT classId,  st.name AS studentName, t.name AS teacherName, level, status,\n" +
//                "       price, s.name AS subjectName, a1.request AS request, a1.reply AS reply, a1.deleted AS deleted\n" +
//                "FROM (SELECT * FROM classes WHERE teacherid = ?) AS a1 JOIN users st ON st.userId = a1.studentId\n" +
//                "             JOIN users t ON t.userid = a1.teacherid JOIN subject s on a1.subjectid = s.subjectid";
//        List<ClassInfo> list = jdbcTemplate.query(query, new Object[]{id}, CLASS_INFO_ROW_MAPPER);
//        return list.isEmpty() ? new ArrayList<>() : list;
//    }
//
//    @Override
//    public Class create(Long studentId, Long teacherId, int level, Long subjectId, int price, int status, String message) {
//        final Map<String, Object> args = new HashMap<>();
//        args.put("studentid", studentId);
//        args.put("teacherid", teacherId);
//        args.put("level", level);
//        args.put("subjectid", subjectId);
//        args.put("price", price);
//        args.put("status", status);
//        args.put("request", message);
//        args.put("reply","");
//        args.put("deleted",0);
//        final Number classId = jdbcInsert.executeAndReturnKey(args);
//        return new Class((long) classId.intValue(), studentId, teacherId, level, subjectId, price, status, message, "",0);
//    }
//
//    @Override
//    public int setStatus(Long classId, int status) {
//        return jdbcTemplate.update("UPDATE classes SET status = ? WHERE classid = ?", status, classId);
//    }
//
//    @Override
//    public int setDeleted(Long classId, int deleted) {
//        return jdbcTemplate.update("UPDATE classes SET deleted = ? WHERE classid = ?", deleted, classId);
//    }
//
//    @Override
//    public int setRequest(Long classId, String message) {
//        return jdbcTemplate.update("UPDATE classes SET request = ? WHERE classid = ?", message, classId);
//    }
//
//    @Override
//    public int setReply(Long classId, String message) {
//        return jdbcTemplate.update("UPDATE classes SET reply = ? WHERE classid = ?", message, classId);
//    }
}
