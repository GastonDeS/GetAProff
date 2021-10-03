package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
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
        mav.addObject("user", userService.findById(uid));
        List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(uid);
        mav.addObject("subjects", subjectsGiven);
        return mav;
    }

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.POST)
    public ModelAndView contact(@PathVariable("uid") final int uid, @ModelAttribute("contactForm") @Valid final ContactForm form,
                                final BindingResult errors) {
        if (errors.hasErrors()) {
            return contactForm(form, uid);
        }
        User user = userService.findById(uid);
        User curr = userService.getCurrentUser();
        Teaches t = teachesService.findByUserAndSubject(uid, form.getSubjectId());
        if (t == null) {
            throw new NotFoundException("Subject " + form.getSubjectId() + " not taught by user " + uid);
        }
        classService.create(curr.getId(), uid, t.getLevel(), t.getSubjectId(), t.getPrice(), Class.Status.PENDING.getValue());
        Subject subject = subjectService.findById(form.getSubjectId());
        if (subject == null) {
            throw new NotFoundException("Cannot find subject for required id: " + form.getSubjectId());
        }
        emailService.sendContactMessage(user.getMail(), "GetAProff: Nueva petición de clase", curr.getName(), subject.getName(), form.getMessage());
        return new ModelAndView("redirect:/emailSent");
    }

    @RequestMapping("/emailSent")
    public ModelAndView emailSent() {
        return new ModelAndView("emailSent");
    }
}
