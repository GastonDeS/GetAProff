package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ClassService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.RatingService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.MailNotSentException;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.InvalidOperationException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.forms.AcceptForm;
import ar.edu.itba.paw.webapp.forms.RateForm;
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
import java.util.stream.Collectors;

@Controller
public class ClassesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/myClasses")
    public ModelAndView myClasses() {
        final ModelAndView mav = new ModelAndView("classes");
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        LOGGER.debug("Accessing classes of user with id: " + user.get().getId());
        mav.addObject("user", user.get());
        List<Class> teacherClassList = classService.findClassesByTeacherId(user.get().getId());
        List<Class> classList = classService.findClassesByStudentId(user.get().getId());
        mav.addObject("teacherPendingClasses", teacherClassList.stream().filter(aClass -> aClass.getStatus() == Class.Status.PENDING.getValue()).collect(Collectors.toList()));
        mav.addObject("teacherActiveClasses", teacherClassList.stream().filter(aClass -> aClass.getStatus() == Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        mav.addObject("teacherFinishedClasses", teacherClassList.stream().filter(aClass -> aClass.getStatus() > Class.Status.ACCEPTED.getValue() && aClass.getDeleted() != Class.Deleted.TEACHER.getValue() && aClass.getDeleted() != Class.Deleted.BOTH.getValue()).collect(Collectors.toList()));
        mav.addObject("isTeacher", user.get().isTeacher() ? 1 : 0);
        mav.addObject("pendingClasses", classList.stream().filter(aClass -> aClass.getStatus() == Class.Status.PENDING.getValue()).collect(Collectors.toList()));
        mav.addObject("activeClasses", classList.stream().filter(aClass -> aClass.getStatus() == Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        mav.addObject("finishedClasses", classList.stream().filter(aClass -> aClass.getStatus() > Class.Status.ACCEPTED.getValue() && aClass.getDeleted() != Class.Deleted.STUDENT.getValue() && aClass.getDeleted() != Class.Deleted.BOTH.getValue()).collect(Collectors.toList()));
        return mav;
    }

    @RequestMapping("/myClasses?error=true")
    public ModelAndView myClassesError() {
        return myClasses();
    }

    @RequestMapping(value = "/myClasses/{cid}/{status}", method = RequestMethod.POST)
    public ModelAndView classesStatusChange(@PathVariable("cid") final Long cid, @PathVariable final String status) {
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        if (status.equals("STUDENT") || status.equals("TEACHER")){
            if (myClass.get().getDeleted() == 0) {
                classService.setDeleted(cid, Class.Deleted.valueOf(status).getValue());
            }
            else {
                classService.setDeleted(cid, Class.Deleted.BOTH.getValue());
            }
        } else {
            classService.setStatus(cid, Class.Status.valueOf(status).getValue());
            myClass.get().setStatus(Class.Status.valueOf(status).getValue());
            try {
                emailService.sendStatusChangeMessage(myClass.get());
            } catch (MailNotSentException exception) {
                throw new OperationFailedException("exception.failed");
            }
        }
        LOGGER.debug("Class " + cid + "changed to status " + status);
        return new ModelAndView("redirect:/myClasses");
    }


    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.GET)
    public ModelAndView acceptForm(@ModelAttribute("acceptForm") final AcceptForm form, @PathVariable("cid") final Long cid) {
        final ModelAndView mav = new ModelAndView("acceptForm");
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        Optional<User> student = userService.findById(myClass.get().getStudent().getId());
        if (!student.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        return mav.addObject("student", student.get().getName()).addObject("uid",myClass.get().getTeacher().getId());
    }

    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.POST)
    public ModelAndView accept(@PathVariable("cid") final Long cid, @ModelAttribute("acceptForm") @Valid final AcceptForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return acceptForm(form, cid);
        }
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        classService.setStatus(myClass.get().getClassId(), Class.Status.ACCEPTED.getValue());
        classService.setReply(myClass.get().getClassId(), form.getMessage());
        try {
            emailService.sendAcceptMessage(myClass.get().getStudent().getId(), myClass.get().getTeacher().getId(), (long) 3, form.getMessage());
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Class accepted by teacher " + myClass.get().getTeacher().getId() + " for stutent " + myClass.get().getStudent().getId());
        return new ModelAndView("redirect:/myClasses");
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.GET)
    public ModelAndView rateForm(@ModelAttribute("rateForm") final RateForm form, @PathVariable("cid") final Long cid) {
        final ModelAndView mav = new ModelAndView("rateForm");
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        Optional<User> teacher = userService.findById((long) myClass.get().getTeacher().getId());
        if (!teacher.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        return mav.addObject("teacher", teacher.get().getName());
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.POST)
    public ModelAndView rate(@PathVariable("cid") final Long cid, @ModelAttribute("rateForm") @Valid final RateForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return rateForm(form, cid);
        }
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        classService.setStatus(cid, Class.Status.RATED.getValue());
        myClass.get().setStatus(Class.Status.RATED.getValue());
        //TODO: chequear si se agrego
        ratingService.addRating(myClass.get().getTeacher().getId(), myClass.get().getStudent().getId(), form.getRating(), form.getReview());
        try {
            emailService.sendRatedMessage(myClass.get(), form.getRating(), form.getReview());
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Class rated by student " + myClass.get().getStudent().getId() + " for teacher " + myClass.get().getTeacher().getId());
        return new ModelAndView("redirect:/myClasses");
    }
}
