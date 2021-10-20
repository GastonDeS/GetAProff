package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.InvalidOperationException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.forms.ContactForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactHandlingController.class);

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
    public ModelAndView contactForm(@ModelAttribute("contactForm") final ContactForm form, @PathVariable("uid") final Long uid) {
        final ModelAndView mav = new ModelAndView("contactForm");
        Optional<User> maybeUser = userService.findById(uid);
        Optional<User> curr = userService.getCurrentUser();
        if (!curr.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        if (!maybeUser.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        LOGGER.debug("User {} contacting teacher {}", curr.get().getId(), uid);
        mav.addObject("user", maybeUser.get());
        List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(uid);
        mav.addObject("subjects", subjectsGiven);
        mav.addObject("currentUid", curr.get().getId());
        return mav;
    }

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.POST)
    public ModelAndView contact(@PathVariable("uid") final Long uid, @ModelAttribute("contactForm") @Valid final ContactForm form,
                                final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form, uid);
        }
        Optional<User> user = userService.findById(uid);
        Optional<User> curr = userService.getCurrentUser();
        String[] subjectIdAndLevel = form.getSubjectAndLevel().split(",",2);
        Optional<Teaches> t = teachesService.findByUserAndSubjectAndLevel(uid, Long.parseLong(subjectIdAndLevel[0]), Integer.parseInt(subjectIdAndLevel[1]));
        if (!t.isPresent() || !user.isPresent() || !curr.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        classService.create(curr.get().getId(), uid, t.get().getLevel(), t.get().getSubject().getId(), t.get().getPrice(), Class.Status.PENDING.getValue(), form.getMessage());
        Optional<Subject> subject = subjectService.findById(Long.parseLong(subjectIdAndLevel[0]));
        if (!subject.isPresent()) {
            throw new OperationFailedException("exception");
        }
        try {
            emailService.sendContactMessage(user.get().getMail(), curr.get().getName(), subject.get().getName(), form.getMessage());
        } catch (RuntimeException exception) {
            throw new OperationFailedException("exception");
        }
        LOGGER.debug("User {} contacted teacher {}", curr.get().getId(), uid);
        return new ModelAndView("redirect:/myClasses");
    }

}
