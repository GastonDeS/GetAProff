package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FavouritesController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/favourites")
    public ModelAndView favourites() {
        final ModelAndView mav = new ModelAndView("favourites");
        Optional<User> u = userService.getCurrentUser();
        if (!u.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user"); //mandar a login
        }
        Optional<List<CardProfile>> favouritesTutors = userService.getFavourites(u.get().getId());
        mav.addObject("uid", u.get().getId());
        mav.addObject("favouritesTutors", favouritesTutors.isPresent() ? favouritesTutors.get() : new ArrayList<>());
        return mav;
    }

    @RequestMapping(value = "/addFavourite/{tutorId}", method = RequestMethod.POST)
    public ModelAndView addFavourite(@PathVariable("tutorId") final int tutorId) {
        Optional<User> u = userService.getCurrentUser();
        if (!u.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user"); //mandar a login
        }
        userService.addFavourite(tutorId, u.get().getId());
        return new ModelAndView("redirect:/profile/"+tutorId);
    }

    @RequestMapping(value = "/removeFavourite/{tutorId}", method = RequestMethod.POST)
    public ModelAndView removeFavourite(@PathVariable("tutorId") final int tutorId) {
        Optional<User> u = userService.getCurrentUser();
        if (!u.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user"); //mandar a login
        }
        int removed = userService.removeFavourite(tutorId, u.get().getId());
        if (removed == 0) {
            throw new OperationFailedException("exception.failed");
        }
        return new ModelAndView("redirect:/profile/" + tutorId);
    }

}
