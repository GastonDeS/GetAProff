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
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    private void addUserId(ModelAndView mav) {
        Optional<User> u = userService.getCurrentUser();
        u.ifPresent(user -> mav.addObject("uid", user.getId()));
    }

    @RequestMapping(value = "/tutors/{offset}", method = RequestMethod.GET, params = "query")
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery, @PathVariable String offset) {
        final ModelAndView mav = new ModelAndView("tutors");
        addUserId(mav);
        Integer pageQty = userService.getPageQty(searchQuery);
        if (Integer.parseInt(offset) > pageQty) {
            return new ModelAndView("403");
        }
        List<CardProfile> maybeTutors = userService.filterUsers(searchQuery, offset);
        mav.addObject("tutors", maybeTutors);
        mav.addObject("subjects", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("urlParams", "?query=" + searchQuery);
        mav.addObject("offset", offset);
        mav.addObject("pageQty",userService.getPageQty(searchQuery));
        return mav;
    }

    @RequestMapping(value = "/tutors/{offset}", method = RequestMethod.GET, params = {"query","order","price","level","rating"})
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery, @RequestParam(value = "order") @NotNull final String order,
                               @RequestParam(value = "price") @NotNull final String price, @RequestParam(value = "level") @NotNull final String level,
                               @RequestParam(value = "rating") @NotNull final String rating, @PathVariable String offset) {
        final ModelAndView mav = new ModelAndView("tutors");
        String urlParams = "?query=" + searchQuery + "&order=" + order + "&price=" + price +"&level=" + level + "&rating=" + rating;
        addUserId(mav);
        List<CardProfile> maybeTutors = userService.filterUsers(searchQuery,order, price, level, rating, offset);
        mav.addObject("tutors", maybeTutors);
        mav.addObject("subject", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("urlParams", urlParams);
        mav.addObject("offset", offset);
        mav.addObject("pageQty",userService.getPageQty(searchQuery, price, level, rating));
        return mav;
    }
}
