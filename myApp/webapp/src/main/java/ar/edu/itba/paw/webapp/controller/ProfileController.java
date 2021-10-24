package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.daos.RatingDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.exceptions.ProfileNotFoundException;
import ar.edu.itba.paw.webapp.forms.UserForm;
import ar.edu.itba.paw.webapp.validators.UserFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserFormValidator userFormValidator;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RatingService ratingService;

    @InitBinder
    public void initSubjectsBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target != null) {
            if (target.getClass().equals(UserForm.class)) {
                webDataBinder.setValidator(userFormValidator);
            }
        }
    }

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final Long uid) {
        Optional<User> curr = userService.getCurrentUser();
        Optional<User> user = userService.findById(uid);
        if (!user.isPresent()) {
            LOGGER.debug("No profile found for id: {}", uid);
            throw new ProfileNotFoundException("exception.profile");
        }
        ModelAndView mav = new ModelAndView("profile");
        if (curr.isPresent()) {
            if (curr.get().getId().equals(user.get().getId())) {
                mav.addObject("currentUser", curr.get()).addObject("edit", 1);
            }
            else if (!user.get().isTeacher() || teachesService.get(user.get().getId()).isEmpty()) {
                LOGGER.debug("Cannot access profile for id: {}", uid);
                throw new ProfileNotFoundException("exception.profile");
            }
        }
        LOGGER.debug("Accessing profile for id: {}", uid);
        List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(uid);
        mav.addObject("user", user.get())
                .addObject("isFaved", curr.isPresent() && userService.isFaved(uid, curr.get().getId()))
                .addObject("subjectsList", subjectsGiven)
                .addObject("isTeacher", user.get().isTeacher() ? 1 : 0)
                .addObject("rating", ratingService.getRatingById(uid));
        return mav;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("userForm") final UserForm form) {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        User user = maybeUser.get();
        ModelAndView mav = new ModelAndView("userForm").addObject("user", user);
        form.setTeacher(user.isTeacher());
        form.setDescription(user.getDescription());
        form.setSchedule(user.getSchedule());
        form.setName(user.getName());
        return mav;
    }

    @RequestMapping("/editProfile?image=false")
    public ModelAndView noImageFound() {
        return new ModelAndView("redirect:/editProfile");
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView userForm(@ModelAttribute("userForm") @Valid final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors())
            return userForm(form);
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        User user = maybeUser.get();
        Long uid = user.getId();
        if (!user.isTeacher()) {
            roleService.addTeacherRole(uid);
        }
        if (form.getImageFile() != null && form.getImageFile().getSize() > 0) {
            imageService.createOrUpdate(uid, form.getImageFile().getBytes());
        }
        int desc = userService.setUserDescription(uid, form.getDescription());
        int sch = userService.setUserSchedule(uid, form.getSchedule());
        int name = userService.setUserName(uid, form.getName());
        if (desc == 0 || sch == 0 || name == 0) {
            throw new OperationFailedException("exception.edit.profile");
        }
        LOGGER.debug("Profile edited for user: {}", uid);
        String redirect = "redirect:/profile/" + uid;
        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/editProfile?teach=true")
    public ModelAndView changeUserRole() {
        return new ModelAndView("redirect:/editProfile");
    }

}
