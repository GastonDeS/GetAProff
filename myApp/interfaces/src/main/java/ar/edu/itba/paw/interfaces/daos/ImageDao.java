package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    Image createOrUpdate(Long uid, byte[] image);

    Optional<Image> findImageById(Long userId);
}
