package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.InvalidOperationException;
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
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private ImageService imageService;

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

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final int uid) {
        User curr = userService.getCurrentUser();
        User user = userService.findById(uid);
        final ModelAndView mav = new ModelAndView("profile")
                .addObject("user", user)
                .addObject("timetable", userService.getUserSchedule(uid))
                .addObject("description", userService.getUserDescription(uid))
                .addObject("schedule", userService.getUserSchedule(uid))
                .addObject("subjectsList", teachesService.getSubjectInfoListByUser(uid))
                .addObject("image", checkUserImg(uid));
        if (curr == null || curr.getId() != uid) {
            return mav.addObject("edit", 0);
        }
        return mav.addObject("edit", 1);
    }

    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        int uid = userService.getCurrentUser().getId();
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
        int uid = userService.getCurrentUser().getId();
        Optional<Teaches> maybe = teachesService.addSubjectToUser(uid, form.getSubjectid(), form.getPrice(), form.getLevel());
        if (!maybe.isPresent()) {
            throw new InvalidOperationException("Cannot add subject " + form.getSubjectid() + "to required user " + uid, "/editSubjects");
        }
        return subjectsForm(form);
    }

    @RequestMapping(value = "/editSubjects/remove/{sid}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("sid") final int sid) {
        int uid = userService.getCurrentUser().getId();
        if (teachesService.removeSubjectToUser(uid, sid) == 0 ) {
            throw new InvalidOperationException("Cannot remove subject " + sid + "to required user " + uid, "/editSubjects");
        }
        return "redirect:/editSubjects";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("userForm") final UserForm form) {
        int uid = userService.getCurrentUser().getId();
        form.setDescription(userService.getUserDescription(uid));
        form.setSchedule(userService.getUserSchedule(uid));
        return new ModelAndView("userForm")
                .addObject("uid", uid)
                .addObject("image", checkUserImg(uid));
    }

    @RequestMapping(value = "/editProfile?error=true", method = RequestMethod.GET)
    public ModelAndView userFormError(@ModelAttribute("userForm") final UserForm form) {
        return userForm(form);
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView userForm(@RequestParam("file") MultipartFile imageFile, @ModelAttribute("userForm") final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors())
            return userForm(form);
        int uid = userService.getCurrentUser().getId();
        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                if (!imageService.findImageById(uid).isPresent()) {
                    imageService.create(uid, null);
                }
                imageService.changeUserImage(uid, imageFile.getBytes());
            }
        }
        int desc = userService.setUserDescription(uid, form.getDescription());
        int sch = userService.setUserSchedule(uid, form.getSchedule());
        if (desc == 0 || sch == 0) {
            throw new InvalidOperationException("Cannot set user information for: " + uid, "/editProfile");
        }
        String redirect = "redirect:/profile/" + uid;
        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/removeImg", method = RequestMethod.GET)
    public String removeImage() {
        int uid = userService.getCurrentUser().getId();
        if (imageService.removeUserImage(uid) == 0) {
            throw new InvalidOperationException("Cannot remove user image for: " + uid, "/editProfile");
        }
        return "redirect:/editProfile";
    }
}
