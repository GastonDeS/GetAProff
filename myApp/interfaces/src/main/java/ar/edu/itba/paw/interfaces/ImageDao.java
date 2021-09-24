package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    Image create(int uid, int imgLength, byte[] image, String photoType);

    public Optional<Image> findImageById(int id);

}
