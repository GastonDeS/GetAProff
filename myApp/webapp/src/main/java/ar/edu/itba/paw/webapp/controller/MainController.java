package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/")
    public ModelAndView index() {
        Optional<User> curr = userService.getCurrentUser();
        Optional<User> usere = userService.findById(15L);
        List<Subject> subjectList = subjectService.list();
        final ModelAndView mav = new ModelAndView("index")
                .addObject("subjects", subjectList);
        curr.ifPresent(user -> mav.addObject("uid", user.getId()));
        return mav;
    }
}
