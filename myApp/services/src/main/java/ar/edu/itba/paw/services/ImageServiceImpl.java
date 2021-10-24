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

    @Transactional
    @Override
    public Image createOrUpdate(Long userId, byte[] image){
        return imageDao.createOrUpdate(userId, image);
    }

    @Override
    public Optional<Image> findImageById(Long userId) {
        return imageDao.findImageById(userId);
    }

    @Override
    public Integer hasImage(Long userId) {
        return imageDao.findImageById(userId).isPresent() ? 1 : 0;
    }
}
