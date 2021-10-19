package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.RoleDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class RoleDaoJdbc implements RoleDao{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Role> ROW_MAPPER = (rs, rowNum) -> new Role(rs.getLong("roleid"), rs.getString("role"));

    @Autowired
    public RoleDaoJdbc (final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("roles")
                .usingGeneratedKeyColumns("roleid");
    }

    @Override
    public Role create(String role) {
        final Map<String, Object> args = new HashMap<>();
        args.put("role", role);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new Role(userId.longValue(), role);
    }

    @Override
    public Optional<Role> findRoleByName(String role) {
        final List<Role> list = jdbcTemplate.query("SELECT * FROM roles WHERE role = ?", new Object[] { role }, ROW_MAPPER);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public int addRoleToUser(Long roleId, Long userId) {
        return jdbcTemplate.update("INSERT INTO userRoles(roleId, userId) VALUES (?, ?)", roleId, userId);
    }

    @Override
    public List<Role> getUserRoles(Long userid) {
        final List<Role> list = jdbcTemplate.query("SELECT r.roleid as roleid, role FROM roles r JOIN userroles u ON r.roleid = u.roleid WHERE userid = ?",
                new Object[] {userid}, ROW_MAPPER);
        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
