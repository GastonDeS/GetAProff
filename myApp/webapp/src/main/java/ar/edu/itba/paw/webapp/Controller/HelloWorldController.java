package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.SubjectService;
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
    EmailService emailService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
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

    @RequestMapping("/tutors")
    public ModelAndView tutors() {
        final ModelAndView mav = new ModelAndView("tutors");
//        mav.addObject("materias", .list());
//        mav.addObject("tutors", materiaService.list());
        return mav;
    }

    @RequestMapping("/email")
    public ModelAndView email() {
        emailService.sendTemplateMessage("anaso@itba.edu.ar", "GetAProff: Nueva petición de clase", "sespina@itba.edu.ar", "Hola quiero tus clases de matematica, contactame!");
        return new ModelAndView("redirect:/");
    }
}

