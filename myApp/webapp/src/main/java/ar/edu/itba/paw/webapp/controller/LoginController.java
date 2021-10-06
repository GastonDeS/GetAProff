package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.LoginErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

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
        Optional<User> curr = userService.getCurrentUser();
        if (!curr.isPresent()) {
            throw new LoginErrorException("exception.login"); //mandar al login
        }
        String redirect = "redirect:/profile/" + curr.get().getId();
        return new ModelAndView(redirect);
    }
}
