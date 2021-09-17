package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.webapp.Forms.ContactForm;
import ar.edu.itba.paw.webapp.Forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Controller
public class HelloWorldController {
    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    EmailService emailService;

    @Autowired
    TeachesService teachesService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("materias", subjectService.list());
        mav.addObject("greeting", userService.findById(1));
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("register") final RegisterForm form) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("register") @Valid final RegisterForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return register(form);
        }
        final ModelAndView mav = new ModelAndView("index");
        final User u = userService.create(form.getName(), form.getMail(), form.getPassword(), form.getUserRole());
        mav.addObject("currentUser", u);
        return mav;
    }

    @RequestMapping(value = "/tutors", method = RequestMethod.GET)
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String search) {
        final ModelAndView mav = new ModelAndView("tutors");
        mav.addObject("materias", subjectService.list());
        mav.addObject("tutors", userService.findUsersBySubject(search));
        mav.addObject("weekDays",Timetable.Days.values());
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contactForm(@ModelAttribute("contactForm") final ContactForm form, @RequestParam(value = "uid") @NotNull final int uid,
                                    @RequestParam(value = "subjectName") @NotNull final String subjectName) {
        final ModelAndView mav = new ModelAndView("contactForm");
        mav.addObject("user",userService.findById(uid));
        mav.addObject("subjectName",subjectName);
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView contact(@RequestParam(value = "uid") @NotNull final int uid, @RequestParam(value = "subjectName") @NotNull final String subjectName,
                                @ModelAttribute("contactForm") @Valid final ContactForm form,final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form,uid,subjectName);
        }
        User user = userService.findById(uid);
        emailService.sendTemplateMessage(user.getMail(), "GetAProff: Nueva petición de clase", form.getName(), subjectName,form.getEmail(), form.getMessage());
        return new ModelAndView("redirect:/emailSent");
    }

    @RequestMapping("/emailSent")
    public ModelAndView emailSent() {
        final ModelAndView mav = new ModelAndView("emailSent");
        return mav;
    }

//    @RequestMapping("/default")
//    public String defaultAfterLogin(HttpServletRequest request) {
//        return request.isUserInRole("ROLE_TEACHER") ? "redirect:/" : "redirect:/";
//    }
}

