package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
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

    @Autowired
    private TeachesService teachesService;

    @RequestMapping("/")
    public ModelAndView index() {
        Optional<User> curr = userService.getCurrentUser();
        List<Subject> subjectList = subjectService.list();
        final ModelAndView mav = new ModelAndView("index")
                .addObject("subjects", subjectList)
                .addObject("topRated", teachesService.getTopRatedTeachers())
                .addObject("hottest", teachesService.getMostRequested())
                .addObject("hottestSubjects", subjectService.getHottestSubjects());
        curr.ifPresent(user -> mav.addObject("uid", user.getId()));
        return mav;
    }
}
