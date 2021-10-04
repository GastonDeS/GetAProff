package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ImageDao;
import ar.edu.itba.paw.models.Image;
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
public class ImageDaoJdbc implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Image> ROW_MAPPER = (rs, rowNum) ->
            new Image(rs.getInt("userid"), rs.getBytes("image"));

    @Autowired
    public ImageDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("images");
    }

    @Override
    public Image createOrUpdate(int uid, byte[] image) {
        jdbcTemplate.update("insert into images values (?,?)\n" +
                " on conflict on constraint images_pkey\n" +
                "     do update set image = excluded.image;",uid, image);
        return new Image(uid, image);
    }

    @Override
    public Image findImageById(int userId) {
        List<Image> image = jdbcTemplate.query("SELECT * FROM images WHERE userid = ?", new Object[] { userId }, ROW_MAPPER);
        return image.isEmpty() ? null : image.get(0);
    }

    @Override
    public int removeUserImage(int userId) {
        return jdbcTemplate.update("DELETE FROM images WHERE userId = ?", userId);
    }
}
