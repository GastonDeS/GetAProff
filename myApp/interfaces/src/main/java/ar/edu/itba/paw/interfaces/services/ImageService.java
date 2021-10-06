package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageService {
    Image createOrUpdate(int uid, byte[] image);

    Optional<Image> findImageById(int userId);

}
