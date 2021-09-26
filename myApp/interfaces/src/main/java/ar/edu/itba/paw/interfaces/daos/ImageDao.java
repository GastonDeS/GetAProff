package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    Image create(int uid, byte[] image);

    public Optional<Image> findImageById(int id);

}
