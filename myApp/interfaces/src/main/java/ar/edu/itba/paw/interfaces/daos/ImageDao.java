package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    Image createOrUpdate(int uid, byte[] image);

    Image findImageById(int userId);

    int removeUserImage(int userId);
}
