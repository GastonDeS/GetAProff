package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.InvalidOperationException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class FavouritesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavouritesController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/favourites")
    public ModelAndView favourites() {
        final ModelAndView mav = new ModelAndView("favourites");
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        LOGGER.debug("Accessing favourites for user {}", user.get().getId());
        List<TeacherInfo> favouritesTutors = userService.getFavourites(user.get().getId());
        mav.addObject("uid", user.get().getId());
        mav.addObject("favouritesTutors", favouritesTutors);
        return mav;
    }

    @RequestMapping(value = "/addFavourite/{tutorId}", method = RequestMethod.POST)
    public ModelAndView addFavourite(@PathVariable("tutorId") final Long tutorId) {
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        if (user.get().getId().equals(tutorId)) throw new InvalidOperationException("exception.invalid");
        int added = userService.addFavourite(tutorId, user.get().getId());
        if (added == 0) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Tutor {} added as favourite for user {}", tutorId, user.get().getId());
        return new ModelAndView("redirect:/profile/" + tutorId);
    }

    @RequestMapping(value = "/removeFavourite/{tutorId}", method = RequestMethod.POST)
    public ModelAndView removeFavourite(@PathVariable("tutorId") final Long tutorId) {
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        int removed = userService.removeFavourite(tutorId, user.get().getId());
        if (removed == 0) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Tutor {} added as favourite for user {}", tutorId, user.get().getId());
        return new ModelAndView("redirect:/profile/" + tutorId);
    }

}
