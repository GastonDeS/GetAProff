package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.LoginErrorException;
import org.jboss.logging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/login?error=true")
    public ModelAndView loginError() {
        return new ModelAndView("login");
    }

    @RequestMapping("/default")
    public ModelAndView defaultRedirect() {
        Optional<User> currentUser = userService.getCurrentUser();
        if (!currentUser.isPresent()) {
            throw new LoginErrorException("exception.login");
        }
        if (!imageService.findImageById(currentUser.get().getId()).isPresent()) {
            return new ModelAndView("redirect:/editProfile?image=false");
        }
        String redirect = "redirect:/profile/" + currentUser.get().getId();
        return new ModelAndView(redirect);
    }
}
