package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ImageDao;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image create(int uid, int imgLength, byte[] image, String photoType){
        return imageDao.create(uid, imgLength, image, photoType);
    }

    @Override
    public Optional<Image> findImageById(int id) {
        return imageDao.findImageById(id);
    }
}
