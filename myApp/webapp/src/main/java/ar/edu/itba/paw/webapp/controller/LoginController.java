package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/default")
    public ModelAndView defaultRedirect() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = userService.findByEmail(auth.getName()).get();
        if (user.getUserRole() == 1) {
            String redirect = "redirect:/profile/" + user.getId();
            return new ModelAndView(redirect);
        }
        return new ModelAndView("index");
    }
}
