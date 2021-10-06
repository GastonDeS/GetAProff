package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

@Controller
public class ImagesController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{uid}", method = RequestMethod.GET, produces = "image/*")
    public @ResponseBody
    byte[] getImage(@PathVariable("uid") final int uid) {
        Optional<Image> image =  imageService.findImageById(uid);
        if (!image.isPresent()) {
            throw new ImageNotFoundException(""); //cambiar la imagen a no image found
        }
        return image.get().getImage();
    }
}
