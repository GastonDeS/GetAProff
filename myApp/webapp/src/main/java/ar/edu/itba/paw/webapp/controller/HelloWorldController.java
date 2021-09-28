package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.webapp.forms.ContactForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Autowired
    ClassService classService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        Optional<User> curr = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("index")
                .addObject("subjects", subjectService.list())
                .addObject("greeting", userService.findById(1));
        if (!curr.isPresent() || curr.get().getUserRole() == 0){
            return mav;
        }
        return mav.addObject("user", curr.get());
    }

    @RequestMapping(value = "/tutors", method = RequestMethod.GET, params = "query")
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery) {
        final ModelAndView mav = new ModelAndView("tutors");
        addUserId(mav);
        List<CardProfile> users = userService.filterUsers(searchQuery);
        mav.addObject("tutors", users);
        mav.addObject("subjects", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays", Timetable.Days.values());
        return mav;
    }

    @RequestMapping(value = "/tutors", method = RequestMethod.GET, params = {"query", "price", "level"})
    public ModelAndView tutors(@RequestParam(value = "query") @NotNull final String searchQuery, @RequestParam(value = "price") @NotNull final String price,
                               @RequestParam(value = "level") @NotNull final String level) {
        final ModelAndView mav = new ModelAndView("tutors");
        addUserId(mav);
        List<CardProfile> users = userService.filterUsers(searchQuery, price, level);
        mav.addObject("tutors", users);
        mav.addObject("materias", subjectService.list());
        mav.addObject("maxPrice", userService.mostExpensiveUserFee(searchQuery));
        mav.addObject("weekDays", Timetable.Days.values());
        return mav;
    }

    private void addUserId(ModelAndView mav) {
        Optional<User> u = userService.getCurrentUser();
        if (u.isPresent()) {
            User curr = u.get();
            if (curr.getUserRole() == 1) {
                mav.addObject("uid", curr.getId());
            }
        }
    }

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.GET)
    public ModelAndView contactForm(@ModelAttribute("contactForm") final ContactForm form, @PathVariable("uid") final int uid) {
        Optional<User> u = userService.getCurrentUser();
        if (u.isPresent()) {
            final ModelAndView mav = new ModelAndView("contactForm");
            mav.addObject("user", userService.findById(uid));

            List<Teaches> teachesList;
            List<SubjectInfo> subjectsGiven = new ArrayList<>();
            teachesList = teachesService.getSubjectListByUser(uid);
            for(Teaches t : teachesList) {
                String name = subjectService.findById(t.getSubjectId()).get().getName();
                subjectsGiven.add(new SubjectInfo(t.getSubjectId(), name, t.getPrice(), t.getLevel()));
            }
            mav.addObject("subjects", subjectsGiven);
            return mav;
        }
        return new ModelAndView("redirect:/login");
    }



    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.POST)
    public ModelAndView contact(@PathVariable("uid") final int uid, @ModelAttribute("contactForm") @Valid final ContactForm form,
                                final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form, uid);
        }
        User user = userService.findById(uid);
        Optional<User> u = userService.getCurrentUser();
        if (u.isPresent()) {

            List<Teaches> teachesList;
            teachesList = teachesService.getSubjectListByUser(uid);
            Teaches t = teachesList.stream().filter(teaches -> teaches.getSubjectId() == form.getSubjectId()).findFirst().orElse(null);

            classService.create(u.get().getId(), uid, t.getLevel(), t.getSubjectId(), t.getPrice(), Class.Status.PENDING.getValue());
            emailService.sendTemplateMessage(user.getMail(), "GetAProff: Nueva petición de clase", u.get().getName(), subjectService.findById(form.getSubjectId()).get().getName(), u.get().getMail(), form.getMessage());
        }
        return new ModelAndView("redirect:/emailSent");
    }

    @RequestMapping("/emailSent")
    public ModelAndView emailSent() {
        final ModelAndView mav = new ModelAndView("emailSent");
        return mav;
    }

    @RequestMapping("/myClasses")
    public ModelAndView myClasses() {
        final ModelAndView mav = new ModelAndView("classes");
        Optional<User> u = userService.getCurrentUser();
        if (u.isPresent()) {
            mav.addObject("user", u.get());
            List<Class> classList = classService.findClassesByStudentId(u.get().getId());
            mav.addObject("pendingClasses", classList.stream().filter(aClass -> aClass.getStatus() == Class.Status.PENDING.getValue()).collect(Collectors.toList()))
                    .addObject("uid", u.get().getId());
        }
        return mav;
    }
}
