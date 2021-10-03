package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image create(int userId, byte[] image){
        return imageDao.create(userId, image);
    }

    @Override
    public Image findImageById(int userId) {
        return imageDao.findImageById(userId);
    }

    @Override
    public int changeUserImage(int userId, byte[] img) {
        return imageDao.changeUserImage(userId, img);
    }

    @Transactional
    @Override
    public int removeUserImage(int userId) {
        return imageDao.removeUserImage(userId);
    }
}
