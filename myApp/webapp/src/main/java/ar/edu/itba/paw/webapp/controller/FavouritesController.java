package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
//            throw new
            return new ModelAndView("login");
        }
        mav.addObject("uid",u.getId());
        mav.addObject("favouritesTutors", userService.getFavourites(u.getId()));
        return mav;
    }

}
