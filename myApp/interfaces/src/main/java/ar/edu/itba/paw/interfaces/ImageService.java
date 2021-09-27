package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageService {
    Image create(int uid, byte[] image);

    Optional<Image> findImageById(int id);

    int changeUserImage(int userId, byte[] img);
}
