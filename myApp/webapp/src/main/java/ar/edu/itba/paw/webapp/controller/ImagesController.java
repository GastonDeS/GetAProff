package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ImagesController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{uid}", method = RequestMethod.GET, produces = "image/*")
    public @ResponseBody
    byte[] getImage(@PathVariable("uid") final int uid) {
        Optional<Image> image =  imageService.findImageById(uid);
        return image.map(Image::getImage).orElse(null);
    }

}
