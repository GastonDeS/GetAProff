package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.MateriaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
    @Autowired
    UserService userService;

    @Autowired
    MateriaService materiaService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("materias",materiaService.list());
        mav.addObject("greeting", userService.findById(1));
        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam(value = "name") final String username) {
        final ModelAndView mav = new ModelAndView("index");
        final User u = userService.create(username);
        mav.addObject("currentUser", u.getName());
        return mav;
}
}
