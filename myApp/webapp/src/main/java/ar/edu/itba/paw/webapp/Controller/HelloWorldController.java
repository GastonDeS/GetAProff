package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.Forms.TutorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView tutorForm(final TutorForm form) {
        final ModelAndView mav = new ModelAndView("tutorForm");
        mav.addObject("tutorForm", form);
        mav.addObject("days", Timetable.Days.values());
        mav.addObject("subjects", subjectService.list());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid final TutorForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return tutorForm(form);
        }
        final ModelAndView mav = new ModelAndView("index");
        final User u = userService.create(form.getName(), form.getMail());
        mav.addObject("currentUser", u);
        mav.addObject("materias", subjectService.list());
        return mav;
    }

    @RequestMapping("/tutors")
    public ModelAndView tutors() {
        final ModelAndView mav = new ModelAndView("tutors");
        return mav;
    }

    @RequestMapping("/email")
    public ModelAndView email() {
        emailService.sendTemplateMessage("anaso@itba.edu.ar", "GetAProff: Nueva petición de clase", "sespina@itba.edu.ar", "Hola quiero tus clases de matematica, contactame!");
        return new ModelAndView("redirect:/");
    }
}

