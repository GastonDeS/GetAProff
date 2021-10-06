package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.forms.NewSubjectForm;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import ar.edu.itba.paw.webapp.forms.UserForm;
import ar.edu.itba.paw.webapp.validators.SubjectsFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubjectsFormValidator subjectsFormValidator;

    @InitBinder
    public void initSubjectsBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target != null) {
            if (target.getClass().equals(SubjectsForm.class)) {
                webDataBinder.setValidator(subjectsFormValidator);
            }
        }
    }

    private User getCurrUser() {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        return maybeUser.get();
    }

    @RequestMapping(value = "/newSubjectForm/{uid}", method = RequestMethod.GET)
    public ModelAndView newSubjectForm(@ModelAttribute("newSubjectForm") final NewSubjectForm form, @PathVariable("uid") final int uid) {
        return new ModelAndView("newSubjectForm");
    }

    @RequestMapping(value = "/newSubjectForm/{uid}", method = RequestMethod.POST)
    public ModelAndView newSubject(@PathVariable("uid") final int uid, @ModelAttribute("newSubjectForm") @Valid final NewSubjectForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return newSubjectForm(form, uid);
        }
        try {
            emailService.sendSubjectRequest(uid, form.getSubject(), form.getMessage());
        } catch (RuntimeException exception) {
            throw new OperationFailedException("exception.failed");
        }
        return new ModelAndView("redirect:/newSubjectFormSent/" + uid);
    }

    @RequestMapping("/newSubjectFormSent/{uid}")
    public ModelAndView classRequestSent(@PathVariable("uid")final int uid) {
        final ModelAndView mav = new ModelAndView("subjectRequestSent");
        mav.addObject("uid", uid);
        return mav;
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        int uid = getCurrUser().getId();
        List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(uid);
        return new ModelAndView("subjectsForm")
                .addObject("userid", uid)
                .addObject("given", subjectsGiven)
                .addObject("subjects", subjectService.list());
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        int uid = getCurrUser().getId();
        Optional<Teaches> maybe = teachesService.addSubjectToUser(uid, form.getSubjectid(), form.getPrice(), form.getLevel());
        if (!maybe.isPresent()) {
            throw new OperationFailedException("exception.failed");
        }
        return subjectsForm(form);
    }

    @RequestMapping(value = "/editSubjects/remove/{sid}", method = RequestMethod.POST)
    public ModelAndView removeSubject(@PathVariable("sid") final int sid) {
        int uid = getCurrUser().getId();
        if (teachesService.removeSubjectToUser(uid, sid) == 0 ) {
            throw new OperationFailedException("exception.failed");
        }
        return new ModelAndView("redirect:/editSubjects");
    }
}
