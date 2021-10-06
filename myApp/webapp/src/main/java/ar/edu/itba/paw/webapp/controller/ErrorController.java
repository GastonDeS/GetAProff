package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;

@Controller
public class ErrorController {

    @Autowired
    private UserService userService;

    @RequestMapping("/403")
    public ModelAndView helloWorld() {
        Optional<User> curr = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("403");
        curr.ifPresent(user -> mav.addObject("uid", user.getId()));
        return mav;
    }
}
