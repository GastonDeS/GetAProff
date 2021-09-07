package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.TimetableService;
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
    SubjectService subjectService;

    @Autowired
    TimetableService ts;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        System.out.println(userService.findById(1));
        ts.createUserSchedule(1, new String[] {"1", "2","3","4","5","6","7"});
        for(String s : ts.getUserSchedule(1))
            System.out.println(s);
        mav.addObject("greeting", userService.findById(1));
        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam(value = "name") final String username,
                               @RequestParam( value = "mail") final String mail) {
        final ModelAndView mav = new ModelAndView("index");
        final User u = userService.create(username, mail);
        mav.addObject("currentUser", u.getName());
        return mav;
    }
}
