package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {

    @Autowired
    UserService userService;

    @RequestMapping("/default")
    public ModelAndView defaultRedirect() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = userService.findByEmail(auth.getName()).get();
        if (user.getUserRole() == 1) {
            return new ModelAndView("subjectsForm"); //Change to teacher profile (for test only)
        }
        return new ModelAndView("index");
    }
}
