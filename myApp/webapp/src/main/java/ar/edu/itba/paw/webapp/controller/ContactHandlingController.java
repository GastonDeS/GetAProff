package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.forms.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ContactHandlingController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.GET)
    public ModelAndView contactForm(@ModelAttribute("contactForm") final ContactForm form, @PathVariable("uid") final int uid) {
        final ModelAndView mav = new ModelAndView("contactForm");
        Optional<User> maybeUser = userService.findById(uid);
        if (!maybeUser.isPresent()) {
            throw new UserNotFoundException("exception.not.user");
        }
        mav.addObject("user", maybeUser.get());
        Optional<List<SubjectInfo>> subjectsGiven = teachesService.getSubjectInfoListByUser(uid);
        if (!subjectsGiven.isPresent()) {
            throw new NotFoundException("exception.no.subjects");
        }
        mav.addObject("subjects", subjectsGiven.get());
        return mav;
    }

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.POST)
    public ModelAndView contact(@PathVariable("uid") final int uid, @ModelAttribute("contactForm") @Valid final ContactForm form,
                                final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form, uid);
        }
        Optional<User> user = userService.findById(uid);
        Optional<User> curr = userService.getCurrentUser();
        Optional<Teaches> t = teachesService.findByUserAndSubject(uid, form.getSubjectId());
        if (!t.isPresent()) {
            throw new NotFoundException("Subject " + form.getSubjectId() + " not taught by user " + uid);
        }
        if (!user.isPresent() || !curr.isPresent()) {
            throw new NotFoundException("exception.user.not.found");
        }
        classService.create(curr.get().getId(), uid, t.get().getLevel(), t.get().getSubjectId(), t.get().getPrice(), Class.Status.PENDING.getValue(), form.getMessage());
        Optional<Subject> subject = subjectService.findById(form.getSubjectId());
        if (!subject.isPresent()) {
            throw new OperationFailedException("exception"); //manadar a 403
        }
        try {
            emailService.sendContactMessage(user.get().getMail(), curr.get().getName(), subject.get().getName(), form.getMessage());
        } catch (RuntimeException e) {
            throw new OperationFailedException("exception");
        }
        return new ModelAndView("redirect:/myClasses");
    }

}
