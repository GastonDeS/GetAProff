package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.InvalidOperationException;
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

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final int uid) {
        User curr = userService.getCurrentUser();
        User user = userService.findById(uid);
        if (user == null) {
            throw new ProfileNotFoundException("Profile not found for requested id: " + uid);
        }
        return new ModelAndView("profile")
                .addObject("user", user)
                .addObject("isFaved", curr != null && userService.isFaved(uid, curr.getId()))
                .addObject("timetable", userService.getUserSchedule(uid))
                .addObject("description", userService.getUserDescription(uid))
                .addObject("schedule", userService.getUserSchedule(uid))
                .addObject("subjectsList", teachesService.getSubjectInfoListByUser(uid))
                .addObject("image", imageService.findImageById(uid) == null ? 0 : 1)
                .addObject("currentUser",curr)
                .addObject("isTeacher",user.isTeacher())
                .addObject("edit", (curr != null && curr.getId() == user.getId())? 1: 0);
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        int uid = userService.getCurrentUser().getId();
        List<Subject> subjectsNotGiven = subjectService.list();
        return new ModelAndView("subjectsForm")
                    .addObject("userid", uid)
                    .addObject("given", teachesService.getSubjectInfoListByUser(uid))
                    .addObject("subjects", subjectsNotGiven);
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        int uid = userService.getCurrentUser().getId();
        Optional<Teaches> maybe = teachesService.addSubjectToUser(uid, form.getSubjectid(), form.getPrice(), form.getLevel());
        if (!maybe.isPresent()) {
            throw new InvalidOperationException("Cannot add subject " + form.getSubjectid() + "to required user " + uid, "/editSubjects");
        }
        return subjectsForm(form);
    }

    @RequestMapping(value = "/editSubjects/remove/{sid}", method = RequestMethod.POST)
    public ModelAndView removeSubject(@PathVariable("sid") final int sid) {
        int uid = userService.getCurrentUser().getId();
        if (teachesService.removeSubjectToUser(uid, sid) == 0 ) {
            throw new InvalidOperationException("Cannot remove subject " + sid + "to required user " + uid, "/editSubjects");
        }
        return new ModelAndView("redirect:/editSubjects");
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("userForm") final UserForm form) {
        int uid = userService.getCurrentUser().getId();
        ModelAndView mav = new ModelAndView("userForm").addObject("uid", uid);
        form.setDescription(userService.getUserDescription(uid));
        form.setSchedule(userService.getUserSchedule(uid));
        Image maybeImg = imageService.findImageById(uid);
        form.setHasImage(maybeImg != null && maybeImg.getImage().length > 0);
        return mav.addObject("image", maybeImg != null);
    }

//    @RequestMapping(value = "/editProfile?error=true", method = RequestMethod.GET)
//    public ModelAndView userFormError(@ModelAttribute("userForm") final UserForm form) {
//        return userForm(form);
//    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView userForm(@ModelAttribute("userForm") @Valid final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors())
            return userForm(form);
        int uid = userService.getCurrentUser().getId();
        if ( form.getImageFile().getSize() > 0) {
            imageService.createOrUpdate(uid, form.getImageFile().getBytes());
        }
        int desc = userService.setUserDescription(uid, form.getDescription());
        int sch = userService.setUserSchedule(uid, form.getSchedule());
        if (desc == 0 || sch == 0) {
            throw new InvalidOperationException("Cannot set user information for: " + uid, "/editProfile");
        }
        String redirect = "redirect:/profile/" + uid;
        return new ModelAndView(redirect);
    }
}
