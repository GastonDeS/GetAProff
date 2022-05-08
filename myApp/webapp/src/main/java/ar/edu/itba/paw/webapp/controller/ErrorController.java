package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


//TODO REMOVE ERROR CONTROLLER
@Controller
public class ErrorController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/error")
    public ModelAndView error() {
        Optional<User> curr = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("403");
        curr.ifPresent(user -> mav.addObject("uid", user.getId()));
        mav.addObject("exception", messageSource.getMessage("page.not.found", null, LocaleContextHolder.getLocale()));
        return mav;
    }
}
