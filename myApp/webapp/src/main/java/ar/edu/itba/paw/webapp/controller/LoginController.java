package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login").addObject("exception", 0);
    }

    @RequestMapping("/login?error=true")
    public ModelAndView loginError() {
        return new ModelAndView("login");
    }

    @RequestMapping("/default")
    public ModelAndView defaultRedirect() {
        final User user;
        if (userService.getCurrentUser().isPresent()) {
            user = userService.getCurrentUser().get();
            if (user.getUserRole() == 1) {
                String redirect = "redirect:/profile/" + user.getId();
                return new ModelAndView(redirect);
            }
        }
        return new ModelAndView("index");
    }
}
