package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    Image create(int uid, byte[] image);

    Image findImageById(int userId);

    int changeUserImage(int userId, byte[] img);

    int removeUserImage(int userId);
}
