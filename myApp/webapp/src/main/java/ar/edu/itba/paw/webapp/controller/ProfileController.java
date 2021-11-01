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
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private UserFileService userFileService;

    @InitBinder
    public void initSubjectsBinder(WebDataBinder webDataBinder) {
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
        ModelAndView mav = new ModelAndView("profile").addObject("user", user.get());
        if (curr.isPresent()) {
            mav.addObject("currentUser", curr.get());
            mav.addObject("userFiles", userFileService.getAllUserFiles(curr.get().getId()));
            if (curr.get().getId().equals(user.get().getId())) {
                mav.addObject("edit", 1);
            } else if (!user.get().isTeacher() || teachesService.get(user.get().getId()).isEmpty()) {
                LOGGER.debug("Cannot access profile for id: {}", uid);
                throw new ProfileNotFoundException("exception.profile");
            } else {
                mav.addObject("isFaved", userService.isFaved(uid, curr.get().getId()));
            }
        }
        LOGGER.debug("Accessing profile for id: {}", uid);
        if (user.get().isTeacher()) {
            List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(uid);
            mav.addObject("subjectsList", subjectsGiven)
                    .addObject("rating", ratingService.getRatingById(uid))
                    .addObject("ratingList", ratingService.getTeacherRatings(uid));
        }
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

    @RequestMapping(value = "/profile/{uid}/{pdfName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getUserFile(@PathVariable("uid") final Long uid, @PathVariable("pdfName") final String pdfName){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        headers.add("Content-Disposition", "inline; filename=" + pdfName);

        List<UserFile> userFiles = userFileService.getAllUserFiles(uid);
        UserFile chosenUserFile = userFiles.stream().
                filter(userFile -> Objects.equals(userFile.getFileName(), pdfName)).findFirst().orElseThrow(RuntimeException::new);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(chosenUserFile.getFile(), headers, HttpStatus.OK);
    }

}
