package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TimetableDaoJdbc implements TimetableDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Timetable> ROW_MAPPER = (rs, rowNum) -> new Timetable(rs.getInt("userid"), rs.getString("monday"),
            rs.getString("tuesday"), rs.getString("wednesday"), rs.getString("thursday"),
            rs.getString("friday"), rs.getString("saturday"));

    @Autowired
    public TimetableDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS timetable ("
                + "userId INTEGER,"
                + "monday VARCHAR(100),"
                + "tuesday VARCHAR(100),"
                + "wednesday VARCHAR(100),"
                + "thursday VARCHAR(100),"
                + "friday VARCHAR(100),"
                + "saturday VARCHAR(100),"
                + "FOREIGN KEY (userId) REFERENCES users,"
                + "PRIMARY KEY (userId)"
                + ")");

        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("timetable");
    }

    @Override
    public Timetable get(int userId) {
        List<Timetable> list = jdbcTemplate.query("SELECT * FROM TIMETABLE WHERE userid = ?", new Object[] {userId}, ROW_MAPPER);
        return list.isEmpty() ? null : list.get(0);
    }
}
