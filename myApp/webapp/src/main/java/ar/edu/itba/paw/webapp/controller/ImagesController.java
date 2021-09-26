package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ImagesController {

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "image/{uid}", method = RequestMethod.GET)
    public String getImage(@PathVariable("uid") final int uid) {
        Optional<Image> image = imageService.findImageById(uid);
        if (!image.isPresent()) {
            return null;
        }
        String imgData = DatatypeConverter.printBase64Binary(image.get().getImage());
        String imgRef = "data:image/png;base64,";
        return imgRef.concat(imgData);
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
    public ModelAndView uploadFile() {
        return new ModelAndView("uploadFile");
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("file") MultipartFile imageFile) {
        Optional<User> u = userService.getCurrentUser();
        if (!u.isPresent()) {
            return new ModelAndView("login");
        }
        try {
            imageService.create(u.get().getId(),imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String redirect = "redirect:/profile/" + u.get().getId() + "/subjects";
        return new ModelAndView(redirect);
    }
}
