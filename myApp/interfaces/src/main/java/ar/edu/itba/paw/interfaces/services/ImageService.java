package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageService {
    Image createOrUpdate(int uid, byte[] image);

    Image findImageById(int userId);

    int removeUserImage(int userId);
}
