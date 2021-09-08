package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.Forms.ContactForm;
import ar.edu.itba.paw.webapp.Forms.TutorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Controller
public class HelloWorldController {
    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    TimetableService timetableService;

    @Autowired
    EmailService emailService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("materias", subjectService.list());
        mav.addObject("greeting", userService.findById(1));
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView tutorForm(final TutorForm form) {
        final ModelAndView mav = new ModelAndView("tutorForm");
        mav.addObject("tutorForm", form);
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
    public ModelAndView tutors(@RequestParam(value = "search") @NotNull final String search) {
        final ModelAndView mav = new ModelAndView("tutors");
        mav.addObject("materias", subjectService.list());
        mav.addObject("tutors", userService.findUsersBySubject(search));
        mav.addObject("weekDays",Timetable.Days.values());
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contactForm(final ContactForm form) {
        final ModelAndView mav = new ModelAndView("contactForm");
        mav.addObject("contactForm", form);
        return mav;
    }
    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView contact(@Valid final ContactForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form);
        }
        emailService.sendTemplateMessage("sespina@itba.edu.ar", "GetAProff: Nueva petición de clase", form.getName(), "Matematica",form.getEmail(), form.getMessage());
        return new ModelAndView("redirect:/");
    }
}

