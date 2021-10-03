package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.LoginErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        User curr = userService.getCurrentUser();
        if (curr == null) {
            throw new LoginErrorException("User could not be authenticated. Please try again.");
        }
        String redirect = "redirect:/profile/" + curr.getId();
        return new ModelAndView(redirect);
    }
}
