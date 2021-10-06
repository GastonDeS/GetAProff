package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.ProfileNotFoundException;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import ar.edu.itba.paw.webapp.forms.UserForm;
import ar.edu.itba.paw.webapp.validators.SubjectsFormValidator;
import ar.edu.itba.paw.webapp.validators.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SubjectsFormValidator subjectsFormValidator;

    @Autowired
    private UserFormValidator userFormValidator;

    @InitBinder
    public void initSubjectsBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target != null) {
            if (target.getClass().equals(SubjectsForm.class)) {
                webDataBinder.setValidator(subjectsFormValidator);
            }
            else if (target.getClass().equals(UserForm.class)) {
                webDataBinder.setValidator(userFormValidator);
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

    private List<SubjectInfo> getSubject(int uid) {
        Optional<List<SubjectInfo>> userSubjects = teachesService.getSubjectInfoListByUser(uid);
        if (!userSubjects.isPresent()) {
            throw new ListNotFoundException("exception.list");
        }
        return userSubjects.get();
    }

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final int uid) {
        Optional<User> curr = userService.getCurrentUser();
        Optional<User> user = userService.findById(uid);
        if (!user.isPresent()) {
            throw new ProfileNotFoundException("Profile not found for requested id: " + uid); //mandar a 403
        }
        ModelAndView mav = new ModelAndView("profile");
        if (curr.isPresent()) {
            if (!user.get().isTeacher() && curr.get().getId() != user.get().getId()) {
                throw new ProfileNotFoundException("Profile not found for requested id: " + uid); //mandar a 403
            }
            mav.addObject("currentUser", curr.get()).addObject("edit", curr.get().getId() == user.get().getId() ? 1 : 0);
        }
        List<SubjectInfo> subjectsGiven = getSubject(uid);
        mav.addObject("user", user.get())
                .addObject("isFaved", curr.isPresent() && userService.isFaved(uid, curr.get().getId()))
                .addObject("subjectsList", subjectsGiven)
                .addObject("image", !imageService.findImageById(uid).isPresent() ? 0 : 1)
                .addObject("isTeacher", user.get().isTeacher() ? 1 : 0);
        return mav;
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        int uid = getCurrUser().getId();
        Optional<List<Subject>> subjects = subjectService.list();
        List<SubjectInfo> subjectsGiven = getSubject(uid);
        return new ModelAndView("subjectsForm")
                    .addObject("userid", uid)
                    .addObject("given", subjectsGiven)
                    .addObject("subjects", subjects.get());
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        int uid = getCurrUser().getId();
        Optional<Teaches> maybe = teachesService.addSubjectToUser(uid, form.getSubjectid(), form.getPrice(), form.getLevel());
        if (!maybe.isPresent()) {
            throw new OperationFailedException("exception.add.subject"); //mandar a 403 con profile
        }
        return subjectsForm(form);
    }

    @RequestMapping(value = "/editSubjects/remove/{sid}", method = RequestMethod.POST)
    public ModelAndView removeSubject(@PathVariable("sid") final int sid) {
        int uid = getCurrUser().getId();
        if (teachesService.removeSubjectToUser(uid, sid) == 0 ) {
            throw new OperationFailedException("exception.remove.subject"); //mandar a 403 con profile
        }
        return new ModelAndView("redirect:/editSubjects");
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("userForm") final UserForm form) {
        User u = getCurrUser();
        ModelAndView mav = new ModelAndView("userForm").addObject("uid", u.getId());
        form.setDescription(u.getDescription());
        form.setSchedule(u.getSchedule());
        Optional<Image> maybeImg = imageService.findImageById(u.getId());
        form.setHasImage(maybeImg.isPresent() && maybeImg.get().getImage().length > 0);
        return mav.addObject("image", maybeImg.isPresent());
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView userForm(@ModelAttribute("userForm") @Valid final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors())
            return userForm(form);
        int uid = getCurrUser().getId();
        if ( form.getImageFile().getSize() > 0) {
            imageService.createOrUpdate(uid, form.getImageFile().getBytes());
        }
        int desc = userService.setUserDescription(uid, form.getDescription());
        int sch = userService.setUserSchedule(uid, form.getSchedule());
        if (desc == 0 || sch == 0) {
            throw new OperationFailedException("exception.edit.profile"); //mandar a 403 con profile
        }
        String redirect = "redirect:/profile/" + uid;
        return new ModelAndView(redirect);
    }
}
