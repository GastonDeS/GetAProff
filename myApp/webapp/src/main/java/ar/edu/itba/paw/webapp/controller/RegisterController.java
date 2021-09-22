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
import ar.edu.itba.paw.webapp.forms.TimeRangeForm;
import ar.edu.itba.paw.webapp.validators.RegisterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    Validator registerFormValidator;

    @Autowired
    TeachesService teachesService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == RegisterForm.class) {
            webDataBinder.setValidator(registerFormValidator);
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
        userService.create(form.getName(), form.getMail(), form.getPassword(), form.getUserRole());
        int id = userService.getCurrentUser().get().getId();

        if (form.getUserRole() == 1) {
            String redirect = "redirect:/profile/" + id;
            return new ModelAndView(redirect);
//            return new ModelAndView("subjectsForm").addObject("subjectsFrom", new SubjectsForm());
        }
        return new ModelAndView("index");
    }

    // Get subject form
    @RequestMapping(value = "/subjectsForm", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        List<Teaches> teachesList;
        List<SubjectInfo> subjectsList = new ArrayList<>();
        if (userService.getCurrentUser().isPresent()) {
            int uid = userService.getCurrentUser().get().getId();
            teachesList = teachesService.getSubjectListByUser(uid);
            for(Teaches t : teachesList) {
                String name = subjectService.findById(t.getSubjectId()).get().getName();
                subjectsList.add(new SubjectInfo(t.getSubjectId(), name, t.getPrice(), t.getLevel()));
            }
            return new ModelAndView("subjectsForm").addObject("subjects", subjectsList);
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
        if (form.getName() == null) {
            return subjectsForm(form);
        }
        Subject s = subjectService.create(form.getName());
        teachesService.addSubjectToUser(uid, s.getId(), form.getPrice(), form.getLevel());
        return subjectsForm(form);
    }

    @RequestMapping(value = "/remove/{sid}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("sid") final int sid) {
        if (userService.getCurrentUser().isPresent()) {
            int uid = userService.getCurrentUser().get().getId();
            teachesService.removeSubjectToUser(uid, sid);
            return "redirect:/subjectsForm";
        }
        return "redirect:/login";
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

    public Validator getRegisterFormValidator() {
        return registerFormValidator;
    }

    public void setRegisterFormValidator(RegisterFormValidator registerFormValidator) {
        this.registerFormValidator = registerFormValidator;
    }
}
