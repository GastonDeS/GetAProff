package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

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
        Optional<User> maybeUser = userService.findByEmail(auth.getName());
        if (!maybeUser.isPresent()) {
            return login();
        }
        final User user = maybeUser.get();
        if (user.getUserRole() == 1) {
            String redirect = "redirect:/profile/" + user.getId();
            return new ModelAndView(redirect);
        }
        return new ModelAndView("index");
    }
}
