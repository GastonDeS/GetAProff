package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    private void addUserId(ModelAndView mav) {
        User u = userService.getCurrentUser();
        if (u != null) {
            mav.addObject("uid", u.getId());
        }
    }

    @RequestMapping(value = "/tutors/1", method = RequestMethod.GET, params = "query")
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery) {
        final ModelAndView mav = new ModelAndView("tutors");
        addUserId(mav);
        List<CardProfile> tutors = userService.filterUsers(searchQuery);
        mav.addObject("tutors", tutors);
        mav.addObject("subjects", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays", Timetable.Days.values());
        return mav;
    }

    @RequestMapping(value = "/tutors/{offset}", method = RequestMethod.GET, params = {"query", "order","price","level","rating"})
    public ModelAndView tutors(@PathVariable (value = "offset") final String offset, @RequestParam(value = "query") @NotNull final String searchQuery, @RequestParam(value = "order") @NotNull final String order,
                               @RequestParam(value = "price") @NotNull final String price, @RequestParam(value = "level") @NotNull final String level,
                               @RequestParam(value = "rating") @NotNull final String rating) {
        final ModelAndView mav = new ModelAndView("tutors");
        addUserId(mav);
        List<CardProfile> users = userService.filterUsers(searchQuery,order, price, level, rating, offset);
        mav.addObject("tutors", users);
        mav.addObject("materias", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays", Timetable.Days.values());
        return mav;
    }
}
