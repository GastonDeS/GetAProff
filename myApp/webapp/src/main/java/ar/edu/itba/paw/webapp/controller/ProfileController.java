package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    TeachesService teachesService;

    private String[] sections = {"subjects", "schedule"};

    @RequestMapping("/profile/{uid}/{section}")
    public ModelAndView profile(@PathVariable("uid") final int uid, @PathVariable("section") final String section) {
        Optional<User> curr = userService.getCurrentUser();
        User user = userService.findById(uid);
        final ModelAndView mav = new ModelAndView("profile")
                .addObject("user", user)
                .addObject("timetable", userService.getUserSchedule(uid))
                .addObject("section", section)
                .addObject("sections", sections)
                .addObject("description", userService.getUserDescription(uid));
        if (curr.isPresent() && curr.get().getId() == uid) {
            mav.addObject("edit", 1);
        } else {
            mav.addObject("edit", 0);
        }
        switch (section) {
            case "subjects":
                mav.addObject("subjectsList", teachesService.getSubjectInfoListByUser(uid));
                break;
            case "schedule":
                mav.addObject("scheduleText", userService.getUserSchedule(uid));
                break;
        }
        return mav;
    }
}
