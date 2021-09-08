package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.models.Timetable;
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
public class TimetableDaoJdbc implements TimetableDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Timetable> ROW_MAPPER = (rs, rowNum) -> new Timetable(rs.getInt("userid"), new String[] {rs.getString("monday"),
            rs.getString("tuesday"), rs.getString("wednesday"), rs.getString("thursday"),
            rs.getString("friday"), rs.getString("saturday"), rs.getString("sunday")});

    @Autowired
    public TimetableDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("timetable");
    }

    @Override
    public Timetable get(int userId) {
        List<Timetable> list = jdbcTemplate.query("SELECT * FROM TIMETABLE WHERE userid = ?", new Object[] {userId}, ROW_MAPPER);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<String> getUserSchedule(int userId) {
        Timetable userSchedule = this.get(userId);
        if(userSchedule == null)
            return null;
        return userSchedule.getSchedule();
    }

    @Override
    public int setUserSchedule(int userId, String[] newSchedule) {
        //TODO
        return 0;

    }

    @Override
    public Timetable createUserSchedule(int userId, String[] schedule) {
        if(get(userId) == null) {
            final Map<String, Object> args = new HashMap<>();
            args.put("userId", userId);
            for (Timetable.Days d : Timetable.Days.values())
                args.put(d.name().toLowerCase(), schedule[d.ordinal()]);
            jdbcInsert.execute(args);
            return new Timetable(userId, schedule);
        }
        return null;
    }
}
