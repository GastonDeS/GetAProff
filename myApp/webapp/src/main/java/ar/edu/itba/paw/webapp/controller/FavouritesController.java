package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FavouritesController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/favourites")
    public ModelAndView favourites() {
        final ModelAndView mav = new ModelAndView("favourites");
        User u = userService.getCurrentUser();
        if (u == null) {
            return new ModelAndView("login");
        }
//        userService.addFavourite(0,u.getId());
        mav.addObject("uid",u.getId());
        mav.addObject("favouritesTutors", userService.getFavourites(u.getId()));
        return mav;
    }

    @RequestMapping(value = "/addFavourite/{tutorId}", method = RequestMethod.POST)
    public ModelAndView addFavourite(@PathVariable("tutorId") final int tutorId) {
        User u = userService.getCurrentUser();
        if (u == null) {
            throw new ProfileNotFoundException("Profile not found for requested id: " +tutorId);
        }
        userService.addFavourite(tutorId, u.getId());
        return new ModelAndView("redirect:/profile/"+tutorId);
    }

    @RequestMapping(value = "/removeFavourite/{tutorId}", method = RequestMethod.POST)
    public ModelAndView removeFavourite(@PathVariable("tutorId") final int tutorId) {
        User u = userService.getCurrentUser();
        if (u == null) {
            throw new ProfileNotFoundException("Profile not found for requested id: " +tutorId);
        }
        userService.removeFavourite(tutorId, u.getId());
        return new ModelAndView("redirect:/profile/"+tutorId);
    }

}
