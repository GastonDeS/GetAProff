package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.webapp.Forms.ContactForm;
import ar.edu.itba.paw.webapp.Forms.SubjectsForm;
import ar.edu.itba.paw.webapp.Forms.TimeRangeForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Controller
public class HelloWorldController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

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

    @RequestMapping(value = "/tutors", method = RequestMethod.GET, params = "query")
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery) {
        final ModelAndView mav = new ModelAndView("tutors");
        List<CardProfile> users = userService.filterUsers(searchQuery);
        mav.addObject("tutors", users);
        mav.addObject("materias", subjectService.list());
        mav.addObject("maxPrice",userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays",Timetable.Days.values());
        return mav;
    }
    @RequestMapping(value = "/tutors", method = RequestMethod.GET, params = {"query","price","level"})
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery, @RequestParam(value = "price") @NotNull final String price,
                               @RequestParam(value = "level") @NotNull final String level) {
        final ModelAndView mav = new ModelAndView("tutors");
        List<CardProfile> users = userService.filterUsers(searchQuery, price, level);
        mav.addObject("tutors", users);
        mav.addObject("materias", subjectService.list());
        mav.addObject("maxPrice",userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays",Timetable.Days.values());
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contactForm(@ModelAttribute("contactForm") final ContactForm form, @RequestParam(value = "uid") @NotNull final int uid,
                                    @RequestParam(value = "subjectName") @NotNull final String subjectName) {
        final ModelAndView mav = new ModelAndView("contactForm");
        mav.addObject("user", userService.findById(uid));
        mav.addObject("subjectName", subjectName);
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView contact(@RequestParam(value = "uid") @NotNull final int uid, @RequestParam(value = "subjectName") @NotNull final String subjectName,
                                @ModelAttribute("contactForm") @Valid final ContactForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form, uid, subjectName);
        }
        User user = userService.findById(uid);
        emailService.sendTemplateMessage(user.getMail(), "GetAProff: Nueva petición de clase", form.getName(), subjectName, form.getEmail(), form.getMessage());
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

    @RequestMapping(value = "/register/subjectsForm", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        Map<String, Integer> subjects = form.getSubjects();
        return new ModelAndView("subjectsForm").addObject("subjects", subjects);
    }

    @RequestMapping(value = "/register/subjectsForm", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = 0;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String userMail = authentication.getName();
            User u = userService.findByEmail(userMail).get();
            userId = u.getId();
        }
        for (Map.Entry<String, Integer> entry : form.getSubjects().entrySet()) {
            String capitalized = entry.getKey().toUpperCase();
            int subjectId = subjectService.create(capitalized).getId();
            if (userId != 0)
            teachesService.addSubjectToUser(userId, subjectId, entry.getValue());
        }
        return new ModelAndView("index");
    }

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final int uid) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", userService.findById(uid));
        return mav;
    }

    @RequestMapping(value = "/timeRegister", method = RequestMethod.GET)
    public ModelAndView timeRegister(@ModelAttribute("timeRangeForm") final TimeRangeForm form) {
        return new ModelAndView("timeForm");
    }

    @RequestMapping(value = "/timeRegister", method = RequestMethod.POST)
    public ModelAndView timeRegister(@ModelAttribute("timeRangeForm") @Valid final TimeRangeForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return timeRegister(form);

        return new ModelAndView("timeForm");
    }

}
