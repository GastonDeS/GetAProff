package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/")
    public ModelAndView index() {
        User curr = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("index")
                .addObject("subjects", subjectService.list())
                .addObject("greeting", userService.findById(1));
        if (curr != null){
            mav.addObject("uid", curr.getId());
        }
        return mav;
    }

}
