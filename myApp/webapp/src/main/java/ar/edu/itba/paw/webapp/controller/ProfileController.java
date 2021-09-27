package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import ar.edu.itba.paw.webapp.forms.TimeForm;
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

    private String[] sections = {"subjects", "schedule"};

    @RequestMapping("/profile/{uid}/{section}")
    public ModelAndView profile(@PathVariable("uid") final int uid, @PathVariable("section") final String section) {
        Optional<User> curr = userService.getCurrentUser();
        User user = userService.findById(uid);
        final ModelAndView mav = new ModelAndView("profile")
                .addObject("user", user)
                .addObject("timetable", userService.getUserSchedule(uid))
                .addObject("section", section)
                .addObject("sections", sections)
                .addObject("description", userService.getUserDescription(uid));
        if (!curr.isPresent() || curr.get().getId() != uid) {
            mav.addObject("edit", 0);
        } else {
            Optional<Image> userImg = imageService.findImageById(curr.get().getId());
            int img = userImg.isPresent() ? 1 : 0;
            mav.addObject("image", img);
            mav.addObject("edit", 1);
        }
        switch (section) {
            case "subjects":
                mav.addObject("subjectsList", teachesService.getSubjectInfoListByUser(uid));
                break;
            case "schedule":
                mav.addObject("scheduleText", userService.getUserSchedule(uid));
                break;
        }
        return mav;
    }

    @RequestMapping(value = "/subjectsForm", method = RequestMethod.GET)
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

    @RequestMapping(value = "/subjectsForm", method = RequestMethod.POST)
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

    @RequestMapping(value = "/subjectsForm/remove/{sid}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("sid") final int sid) {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (maybeUser.isPresent()) {
            int uid = maybeUser.get().getId();
            teachesService.removeSubjectToUser(uid, sid);
            return "redirect:/subjectsForm";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/timeForm", method = RequestMethod.GET)
    public ModelAndView timeForm(@ModelAttribute("timeForm") final TimeForm form) {
        return new ModelAndView("timeForm");
    }

    @RequestMapping(value = "/timeForm", method = RequestMethod.POST)
    public ModelAndView timeForm(@ModelAttribute("timeForm") @Valid final TimeForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return timeForm(form);
        Optional<User> maybeUser = userService.getCurrentUser();
        if (maybeUser.isPresent()){
            int uid = maybeUser.get().getId();
            userService.setUserSchedule(uid, form.getSchedule());
            String redirect = "redirect:/profile/" + uid + "/schedule";
            return new ModelAndView(redirect);
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("userForm") final UserForm form) {
        Optional<User> maybeUser = userService.getCurrentUser();
        ModelAndView mav = new ModelAndView("userForm");
        if (maybeUser.isPresent()) {
            int uid = maybeUser.get().getId();
            mav.addObject("uid", uid)
                    .addObject("description", userService.getUserDescription(uid));
            return mav;
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView userForm(@RequestParam("file") MultipartFile imageFile, @ModelAttribute("userForm") @Valid final UserForm form, final BindingResult errors) throws IOException {
        if (errors.hasErrors())
            return userForm(form);
        Optional<User> maybeUser = userService.getCurrentUser();
        if (maybeUser.isPresent()){
            int uid = maybeUser.get().getId();
            if (imageFile != null) {
                if (!imageService.findImageById(uid).isPresent()) {
                    imageService.create(uid, null);
                }
                imageService.changeUserImage(uid, imageFile.getBytes());
            }
            userService.setUserDescription(uid, form.getDescription());
            String redirect = "redirect:/profile/" + uid + "/subjects";
            return new ModelAndView(redirect);
        }
        return new ModelAndView("login");
    }
}
