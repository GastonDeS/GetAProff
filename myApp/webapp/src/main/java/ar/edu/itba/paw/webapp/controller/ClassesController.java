package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ClassService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.forms.AcceptForm;
import ar.edu.itba.paw.webapp.forms.RateForm;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private EmailService emailService;

    @RequestMapping("/myClasses")
    public ModelAndView myClasses() {
        final ModelAndView mav = new ModelAndView("classes");
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user"); //mandar a login
        }
        mav.addObject("user", user.get());
        Optional<List<ClassInfo>> teacherClassList = classService.findClassesByTeacherId(user.get().getId());
        Optional<List<ClassInfo>> classList = classService.findClassesByStudentId(user.get().getId());
        if (!teacherClassList.isPresent() || !classList.isPresent()) {
            throw new ListNotFoundException("exception"); // mandar a 403
        }
        mav.addObject("teacherPendingClasses", teacherClassList.get().stream().filter(aClass -> aClass.getStatus() == Class.Status.PENDING.getValue()).collect(Collectors.toList()));
        mav.addObject("teacherActiveClasses", teacherClassList.get().stream().filter(aClass -> aClass.getStatus() == Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        mav.addObject("teacherFinishedClasses", teacherClassList.get().stream().filter(aClass -> aClass.getStatus() > Class.Status.ACCEPTED.getValue() && aClass.getDeleted() != Class.Deleted.TEACHER.getValue() && aClass.getDeleted() != Class.Deleted.BOTH.getValue()).collect(Collectors.toList()));
        mav.addObject("isTeacher", user.get().isTeacher() ? 1 : 0);
        mav.addObject("pendingClasses", classList.get().stream().filter(aClass -> aClass.getStatus() == Class.Status.PENDING.getValue()).collect(Collectors.toList()));
        mav.addObject("activeClasses", classList.get().stream().filter(aClass -> aClass.getStatus() == Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        mav.addObject("finishedClasses", classList.get().stream().filter(aClass -> aClass.getStatus() > Class.Status.ACCEPTED.getValue() && aClass.getDeleted() != Class.Deleted.STUDENT.getValue() && aClass.getDeleted() != Class.Deleted.BOTH.getValue()).collect(Collectors.toList()));
        return mav;
    }

    @RequestMapping(value = "/myClasses/{cid}/{status}", method = RequestMethod.POST)
    public ModelAndView classesStatusChange(@PathVariable("cid") final int cid, @PathVariable final String status) {
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("exception.class"); //mandar a 403
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
            try {
                emailService.sendStatusChangeMessage(myClass.get());
            } catch (RuntimeException e) {
                throw new OperationFailedException("exception.failed"); //mandar a 403
            }
        }
        return new ModelAndView("redirect:/myClasses");
    }


    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.GET)
    public ModelAndView acceptForm(@ModelAttribute("acceptForm") final AcceptForm form, @PathVariable("cid") final int cid) {
        final ModelAndView mav = new ModelAndView("acceptForm");
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("exception.class"); //mandar a 403
        }
        Optional<User> student = userService.findById(myClass.get().getStudentId());
        if (!student.isPresent()) {
            throw new UserNotFoundException("User not found"); //mandar a 403
        }
        return mav.addObject("student", student.get().getName());
    }

    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.POST)
    public ModelAndView accept(@PathVariable("cid") final int cid, @ModelAttribute("acceptForm") @Valid final AcceptForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return acceptForm(form, cid);
        }
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("exception.class"); //mandar a 403
        }
        classService.setStatus(myClass.get().getClassId(), Class.Status.ACCEPTED.getValue());
        classService.setReply(myClass.get().getClassId(), form.getMessage());
        try {
            emailService.sendAcceptMessage(myClass.get().getStudentId(), myClass.get().getTeacherId(), 3, form.getMessage());
        } catch (RuntimeException e) {
            throw new OperationFailedException("exception.failed"); //mandar a 403
        }
        return new ModelAndView("redirect:/myClasses");
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.GET)
    public ModelAndView rateForm(@ModelAttribute("rateForm") final RateForm form, @PathVariable("cid") final int cid) {
        final ModelAndView mav = new ModelAndView("rateForm");
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("exception.class"); //mandar a 403
        }
        Optional<User> teacher = userService.findById(myClass.get().getTeacherId());
        if (!teacher.isPresent()) {
            throw new UserNotFoundException("exception.user"); //mandar a 403
        }
        return mav.addObject("teacher", teacher.get().getName());
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.POST)
    public ModelAndView rate(@PathVariable("cid") final int cid, @ModelAttribute("rateForm") @Valid final RateForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return rateForm(form, cid);
        }
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("exception.class"); //mandar a 403
        }
        classService.setStatus(cid, Class.Status.RATED.getValue());
        userService.addRating(myClass.get().getTeacherId(),myClass.get().getStudentId(), form.getRating(), form.getReview());
        try {
            emailService.sendRatedMessage(myClass.get(), form.getRating(), form.getReview());
        } catch (RuntimeException e) {
            throw new OperationFailedException("exception.failed"); //mandar a 403
        }
        return new ModelAndView("redirect:/myClasses");
    }
}
