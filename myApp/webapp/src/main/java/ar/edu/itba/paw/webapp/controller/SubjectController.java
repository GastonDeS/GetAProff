package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.forms.NewSubjectForm;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class SubjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private EmailService emailService;

    private User getCurrUser() {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        return maybeUser.get();
    }

    @RequestMapping(value = "/newSubjectForm/{uid}", method = RequestMethod.GET)
    public ModelAndView newSubjectForm(@ModelAttribute("newSubjectForm") final NewSubjectForm form, @PathVariable("uid") final Long uid) {
        return new ModelAndView("newSubjectForm");
    }

    @RequestMapping(value = "/newSubjectForm/{uid}", method = RequestMethod.POST)
    public ModelAndView newSubject(@PathVariable("uid") final Long uid, @ModelAttribute("newSubjectForm") @Valid final NewSubjectForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return newSubjectForm(form, uid);
        }
        try {
            emailService.sendSubjectRequest(uid, form.getSubject(), form.getMessage());
        } catch (RuntimeException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Subject request sent for {}", uid);
        return new ModelAndView("redirect:/newSubjectFormSent/" + uid);
    }

    @RequestMapping("/newSubjectFormSent/{uid}")
    public ModelAndView classRequestSent(@PathVariable("uid")final Long uid) {
        final ModelAndView mav = new ModelAndView("subjectRequestSent");
        mav.addObject("uid", uid);
        return mav;
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        User currentUser = getCurrUser();
        List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(currentUser.getId());
        return new ModelAndView("subjectsForm")
                .addObject("userid", currentUser.getId())
                .addObject("given", subjectsGiven)
                .addObject("subjects", subjectService.list());
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.POST)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        Long userId = getCurrUser().getId();
        if (teachesService.findByUserAndSubjectAndLevel(userId, form.getSubjectId(), form.getLevel()).isPresent()) {
            errors.rejectValue("level","form.level.invalid");
        }
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        Optional<Teaches> maybe = teachesService.addSubjectToUser(userId, form.getSubjectId(), form.getPrice(), form.getLevel());
        if (!maybe.isPresent()) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Subject added for user {}", userId);
        return subjectsForm(form);
    }

    @RequestMapping(value = "/editSubjects/remove/{sid}/{level}", method = RequestMethod.POST)
    public ModelAndView removeSubject(@PathVariable("sid") final Long sid, @PathVariable("level") final int level) {
        Long uid = getCurrUser().getId();
        if (teachesService.removeSubjectToUser(uid, sid, level) == 0 ) {
            System.out.println("ERROR add subject");
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Subject removed for user {}",uid);
        return new ModelAndView("redirect:/editSubjects");
    }
}
