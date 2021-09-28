package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import ar.edu.itba.paw.webapp.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final int uid) {
        Optional<User> curr = userService.getCurrentUser();
        User user = userService.findById(uid);
        final ModelAndView mav = new ModelAndView("profile")
                .addObject("user", user)
                .addObject("timetable", userService.getUserSchedule(uid))
                .addObject("description", userService.getUserDescription(uid))
                .addObject("schedule", userService.getUserSchedule(uid))
                .addObject("subjectsList", teachesService.getSubjectInfoListByUser(uid));
        if (!curr.isPresent() || curr.get().getId() != uid) {
            mav.addObject("edit", 0);
        } else {
            mav.addObject("edit", 1);
        }
        return mav;
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        if (userService.getCurrentUser().isPresent()) {
            int uid = userService.getCurrentUser().get().getId();
            List<Subject> subjectsNotGiven = subjectService.subjectsNotGiven(uid);
            return new ModelAndView("subjectsForm")
                    .addObject("userid", uid)
                    .addObject("given", teachesService.getSubjectInfoListByUser(uid))
                    .addObject("toGive", subjectsNotGiven);
        }
        return new ModelAndView("login"); //Send to 403
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            return new ModelAndView("login");
        }
        int uid = maybeUser.get().getId();
        teachesService.addSubjectToUser(uid, form.getSubjectid(), form.getPrice(), form.getLevel());
        return subjectsForm(form);
    }

    @RequestMapping(value = "/editSubjects/remove/{sid}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("sid") final int sid) {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (maybeUser.isPresent()) {
            int uid = maybeUser.get().getId();
            teachesService.removeSubjectToUser(uid, sid);
            return "redirect:/editSubjects";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("userForm") final UserForm form) {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (maybeUser.isPresent()) {
            int uid = maybeUser.get().getId();
            String desc = userService.getUserDescription(uid);
            if (desc != null) {
                if (!desc.isEmpty()) form.setDescription(desc);
            }
            String schedule = userService.getUserSchedule(uid);
            if (schedule != null) {
                if (!schedule.isEmpty()) form.setSchedule(schedule);
            }
            return new ModelAndView("userForm")
                    .addObject("uid", uid);
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView userForm(@RequestParam("file") MultipartFile imageFile, @ModelAttribute("userForm") final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors())
            return userForm(form);
        Optional<User> maybeUser = userService.getCurrentUser();
        if (maybeUser.isPresent()){
            int uid = maybeUser.get().getId();
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
        return new ModelAndView("login");
    }
}
