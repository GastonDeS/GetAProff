package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ImageDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImageDaoJdbc implements ImageDao {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Image> ROW_MAPPER = (rs, rowNum) ->
            new Image(rs.getInt("userid"), rs.getInt("imglength"),
            rs.getBytes("image"),rs.getString("phototype"));

    @Autowired
    public ImageDaoJdbc(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("images");
    }

    @Override
    public Image create(int uid, int imgLength, byte[] image, String photoType) {
        final Map<String, Object> args = new HashMap<>();
        args.put("userid", uid);
        args.put("image", image);
        args.put("imglength", imgLength);
        args.put("phototype", photoType);
        jdbcInsert.execute(args);
        return new Image(uid, imgLength, image, photoType);
    }

    @Override
    public Optional<Image> findImageById(int id) {
        return jdbcTemplate.query("SELECT * FROM images WHERE userid = ?", new Object[] { id }, ROW_MAPPER)
                .stream().findFirst();
    }
}
