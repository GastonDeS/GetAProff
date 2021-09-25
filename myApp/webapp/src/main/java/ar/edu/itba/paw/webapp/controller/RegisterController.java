package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.TeachesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import ar.edu.itba.paw.webapp.forms.TimeForm;
import ar.edu.itba.paw.webapp.validators.RegisterFormValidator;
import ar.edu.itba.paw.webapp.validators.SubjectsFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    private RegisterFormValidator registerFormValidator;

    @Autowired
    private SubjectsFormValidator subjectsFormValidator;

    @Autowired
    TeachesService teachesService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target != null) {
            if (target.getClass().equals(RegisterForm.class)) {
                webDataBinder.setValidator(registerFormValidator);
            } else if (target.getClass().equals(SubjectsForm.class)) {
                webDataBinder.setValidator(subjectsFormValidator);
            }
        }
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
        //TODO: validar bien
        User user = userService.create(form.getName(), form.getMail(), form.getPassword(), form.getUserRole()).orElse(null);
        if (form.getUserRole() == 1 && user != null) {
            String redirect = "redirect:/profile/" + user.getId() + "/subjects";
            return new ModelAndView(redirect);
        }
        return new ModelAndView("index");
    }

    //Get subject form
    @RequestMapping(value = "/subjectsForm", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        List<Teaches> teachesList;
        List<Subject> subjectsNotGiven;
        List<SubjectInfo> subjectsGiven = new ArrayList<>();
        if (userService.getCurrentUser().isPresent()) {
            int uid = userService.getCurrentUser().get().getId();
            subjectsNotGiven = subjectService.subjectsNotGiven(uid);
            teachesList = teachesService.getSubjectListByUser(uid);
            for(Teaches t : teachesList) {
                String name = subjectService.findById(t.getSubjectId()).get().getName();
                subjectsGiven.add(new SubjectInfo(t.getSubjectId(), name, t.getPrice(), t.getLevel()));
            }
            return new ModelAndView("subjectsForm")
                    .addObject("userid", uid)
                    .addObject("given", subjectsGiven)
                    .addObject("toGive", subjectsNotGiven);
        }
        return new ModelAndView("login"); //Send to 403
    }

    // Save subject form
    @RequestMapping(value = "/subjectsForm", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        int uid = userService.getCurrentUser().get().getId();
        teachesService.addSubjectToUser(uid, form.getSubjectid(), form.getPrice(), form.getLevel());
        return subjectsForm(form);
    }

    @RequestMapping(value = "/subjectsForm/remove/{sid}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("sid") final int sid) {
        if (userService.getCurrentUser().isPresent()) {
            int uid = userService.getCurrentUser().get().getId();
            teachesService.removeSubjectToUser(uid, sid);
            return "redirect:/subjectsForm";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/timeForm", method = RequestMethod.GET)
    public ModelAndView timeForm(@ModelAttribute("timeForm") final TimeForm form) {
        return new ModelAndView("timeForm");
    }

    @RequestMapping(value = "/timeForm", method = RequestMethod.POST)
    public ModelAndView timeForm(@ModelAttribute("timeForm") @Valid final TimeForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return timeForm(form);
        return new ModelAndView("timeForm");
    }
}
