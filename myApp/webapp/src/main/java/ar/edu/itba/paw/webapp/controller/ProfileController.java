package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import ar.edu.itba.paw.webapp.forms.UserForm;
import ar.edu.itba.paw.webapp.validators.SubjectsFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    TeachesService teachesService;

    @Autowired
    ImageService imageService;

    @Autowired
    private SubjectsFormValidator subjectsFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target != null) {
            if (target.getClass().equals(SubjectsForm.class)) {
                webDataBinder.setValidator(subjectsFormValidator);
            }
        }
    }

    private int checkUserImg(int uid) {
        return imageService.findImageById(uid).isPresent() ? 1 : 0;
    }

    private User getCurrUser() {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            throw new UnauthenticatedUserException("Can't access: unauthenticated user");
        }
        return maybeUser.get();
    }

    private User getCurrUserNoRedirect() {
        Optional<User> maybeUser = userService.getCurrentUser();
        return maybeUser.orElse(null);
    }

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final int uid) {
        Optional<User> curr = userService.getCurrentUser();
        User user = userService.findById(uid);
        final ModelAndView mav = new ModelAndView("profile")
                .addObject("user", user)
                .addObject("timetable", userService.getUserSchedule(uid))
                .addObject("description", userService.getUserDescription(uid))
                .addObject("schedule", userService.getUserSchedule(uid))
                .addObject("subjectsList", teachesService.getSubjectInfoListByUser(uid))
                .addObject("image", checkUserImg(uid));
        if (!curr.isPresent() || curr.get().getId() != uid) {
            return mav.addObject("edit", 0);
        }
        return mav.addObject("edit", 1);
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        int uid = getCurrUser().getId();
        List<Subject> subjectsNotGiven = subjectService.subjectsNotGiven(uid);
        return new ModelAndView("subjectsForm")
                    .addObject("userid", uid)
                    .addObject("given", teachesService.getSubjectInfoListByUser(uid))
                    .addObject("toGive", subjectsNotGiven);
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        int uid = getCurrUserNoRedirect().getId();
        teachesService.addSubjectToUser(uid, form.getSubjectid(), form.getPrice(), form.getLevel());
        return subjectsForm(form);
    }

    @RequestMapping(value = "/editSubjects/remove/{sid}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("sid") final int sid) {
        int uid = getCurrUser().getId();
        teachesService.removeSubjectToUser(uid, sid);
        return "redirect:/editSubjects";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("userForm") final UserForm form) {
        int uid = getCurrUser().getId();
        form.setDescription(userService.getUserDescription(uid));
        form.setSchedule(userService.getUserSchedule(uid));
        return new ModelAndView("userForm")
                .addObject("uid", uid)
                .addObject("image", checkUserImg(uid));
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView userForm(@RequestParam("file") MultipartFile imageFile, @ModelAttribute("userForm") final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors())
            return userForm(form);
        int uid = getCurrUserNoRedirect().getId();
        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                if (!imageService.findImageById(uid).isPresent()) {
                    imageService.create(uid, null);
                }
                imageService.changeUserImage(uid, imageFile.getBytes());
            }
        }
        userService.setUserDescription(uid, form.getDescription());
        userService.setUserSchedule(uid, form.getSchedule());
        String redirect = "redirect:/profile/" + uid;
        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/removeImg", method = RequestMethod.GET)
    public String removeImage() {
        Optional<User> maybeuser = userService.getCurrentUser();
        maybeuser.ifPresent(user -> imageService.removeUserImage(user.getId()));
        return "redirect:/editProfile";
    }
}
