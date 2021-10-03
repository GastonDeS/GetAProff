package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @Autowired
    private UserService userService;

    @RequestMapping("/403")
    public ModelAndView helloWorld() {
        User curr = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("403");
        if (curr != null){
            mav.addObject("uid", curr.getId());
        }
        return mav;
    }
}
