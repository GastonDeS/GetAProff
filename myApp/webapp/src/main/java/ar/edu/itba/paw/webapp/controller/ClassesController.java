package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ClassService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.InvalidOperationException;
import ar.edu.itba.paw.webapp.forms.AcceptForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
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
        User user = userService.getCurrentUser();
        mav.addObject("user", user);
        List<ClassInfo> teacherClassList = classService.findClassesByTeacherId(user.getId());
        mav.addObject("teacherPendingClasses", teacherClassList.stream().filter(aClass -> aClass.getStatus() == Class.Status.PENDING.getValue()).collect(Collectors.toList()));
        mav.addObject("teacherActiveClasses", teacherClassList.stream().filter(aClass -> aClass.getStatus() == Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        mav.addObject("teacherFinishedClasses", teacherClassList.stream().filter(aClass -> aClass.getStatus() > Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        mav.addObject("isTeacher", user.isTeacher() ? 1 : 0);
        List<ClassInfo> classList = classService.findClassesByStudentId(user.getId());
        mav.addObject("pendingClasses", classList.stream().filter(aClass -> aClass.getStatus() == Class.Status.PENDING.getValue()).collect(Collectors.toList()));
        mav.addObject("activeClasses", classList.stream().filter(aClass -> aClass.getStatus() == Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        mav.addObject("finishedClasses", classList.stream().filter(aClass -> aClass.getStatus() > Class.Status.ACCEPTED.getValue()).collect(Collectors.toList()));
        return mav;
    }

    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.GET)
    public ModelAndView acceptForm(@ModelAttribute("acceptForm") final AcceptForm form, @PathVariable("cid") final int cid) {
        final ModelAndView mav = new ModelAndView("acceptForm");
        Class myClass = classService.findById(cid);
        User student = userService.findById(myClass.getStudentId());
        if (student == null) {
            throw new NotFoundException("User not found");
        }
        return mav.addObject("student", student.getName());
    }

    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.POST)
    public ModelAndView accept(@PathVariable("cid") final int cid, @ModelAttribute("acceptForm") @Valid final AcceptForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return acceptForm(form, cid);
        }
        Class myClass = classService.findById(cid);
        classService.setStatus(myClass.getClassId(), Class.Status.ACCEPTED.getValue());
        emailService.sendAcceptMessage(myClass.getStudentId(), "GetAProff: Tu clase fue aceptada", myClass.getTeacherId(), myClass.getSubjectid(), form.getMessage());
        return new ModelAndView("redirect:/myClasses");
    }
}