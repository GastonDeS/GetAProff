package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import ar.edu.itba.paw.webapp.exceptions.ProfileNotFoundException;
import ar.edu.itba.paw.webapp.forms.CertificationForm;
import ar.edu.itba.paw.webapp.forms.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private RoleService roleService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserFileService userFileService;

    @RequestMapping("/profile/{uid}")
    public ModelAndView profile(@PathVariable("uid") final Long uid) {
        Optional<User> curr = userService.getCurrentUser();
        Optional<User> user = userService.findById(uid);
        if (!user.isPresent()) {
            LOGGER.debug("No profile found for id: {}", uid);
            throw new ProfileNotFoundException("exception.profile");
        }
        ModelAndView mav = new ModelAndView("profile").addObject("user", user.get());
        mav.addObject("userFiles", userFileService.getAllUserFiles(uid));
        if (curr.isPresent()) {
            mav.addObject("currentUser", curr.get());
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
    public ModelAndView editProfile(@ModelAttribute("userForm") final UserForm form) {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        User user = maybeUser.get();
        ModelAndView mav = new ModelAndView("userForm").addObject("user", user);
        mav.addObject("userFiles", userFileService.getAllUserFiles(user.getId()));
        form.setDescription(user.getDescription());
        form.setSchedule(user.getSchedule());
        form.setName(user.getName());
        return mav;
    }

    @RequestMapping(value = "/editProfile/startTeaching", method = RequestMethod.GET)
    public ModelAndView startTeachingEdit(@ModelAttribute("userForm") final UserForm form) {
        return editProfile(form).addObject("startTeaching", true);
    }

    @RequestMapping("/editProfile?image=false")
    public ModelAndView noImageFound() {
        return editProfile(new UserForm());
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "startTeaching")
    public ModelAndView startTeachingSubmit(@ModelAttribute("userForm") @Validated(UserForm.Teacher.class) final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return startTeachingEdit(form);
        }
        Long userId = commonEditProfile(form);
        roleService.addTeacherRole(userId);
        userService.setTeacherAuthorityToUser();
        return changeUserData(userId, form);
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "teacher")
    public ModelAndView editProfileTeacherSubmit(@ModelAttribute("userForm") @Validated(UserForm.Teacher.class) final UserForm form,
                                 final BindingResult errors) throws IOException {
        if (errors.hasErrors()){
            return editProfile(form);
        }
        Long userId = commonEditProfile(form);
        return changeUserData(userId, form);
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "student")
    public ModelAndView editProfileStudentSubmit(@ModelAttribute("userForm") @Validated(UserForm.Student.class) final UserForm form,
                                    final BindingResult errors) throws IOException {
        if (errors.hasErrors()){
            return editProfile(form);
        }
        Long userId = commonEditProfile(form);
        int name = userService.setUserName(userId, form.getName());
        if (name == 0) {
            throw new OperationFailedException("exception.edit.profile");
        }
        LOGGER.debug("Profile edited for user: {}", userId);
        String redirect = "redirect:/profile/" + userId;
        return new ModelAndView(redirect);
    }

    private Long commonEditProfile(final UserForm form) throws IOException {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        Long userId = maybeUser.get().getId();
        if (form.getImageFile() != null && form.getImageFile().getSize() > 0) {
            imageService.createOrUpdate(userId, form.getImageFile().getBytes());
        }
        return userId;
    }

    private ModelAndView changeUserData(Long userId, final UserForm form) {
        int desc = userService.setUserDescription(userId, form.getDescription());
        int sch = userService.setUserSchedule(userId, form.getSchedule());
        int name = userService.setUserName(userId, form.getName());
        if (desc == 0 || sch == 0 || name == 0) {
            throw new OperationFailedException("exception.edit.profile");
        }
        LOGGER.debug("Profile edited for user: {}", userId);
        String redirect = "redirect:/profile/" + userId;
        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/profile/{uid}/{pdfName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getUserFile(@PathVariable("uid") final Long uid, @PathVariable("pdfName") final String pdfName){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        headers.add("Content-Disposition", "inline; filename=" + pdfName);
        List<UserFile> userFiles = userFileService.getAllUserFiles(uid);
        UserFile chosenUserFile = userFiles.stream().
                filter(userFile -> Objects.equals(userFile.getFileName(), pdfName)).findFirst().
                orElseThrow(() -> new OperationFailedException("exception.failed"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(chosenUserFile.getFile(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/editCertifications", method = RequestMethod.GET)
    public ModelAndView editCertifications(@ModelAttribute("certificationForm") @Valid final CertificationForm form){
        User currentUser  = userService.getCurrentUser().orElseThrow(() -> new NoUserLoggedException(""));
        List<UserFile> currentUserFiles = userFileService.getAllUserFiles(currentUser.getId());
        return new ModelAndView("editCertifications").
                addObject("userFiles",currentUserFiles);
    }

    @RequestMapping(value = "/editCertifications", method = RequestMethod.POST, params = "submitFile")
    public ModelAndView submitUserFile(@ModelAttribute("certificationForm") @Valid final CertificationForm form) throws IOException {
        MultipartFile fileToUpload = form.getUserFileToUpload();
        User fileOwner  = userService.getCurrentUser().orElseThrow(() -> new NoUserLoggedException("mensaje"));
        userFileService.saveNewFile(fileToUpload.getBytes(),fileToUpload.getOriginalFilename(), fileOwner.getId());
        return new ModelAndView("redirect:/editCertifications");

    }
    @RequestMapping(value = "/editCertifications", method = RequestMethod.POST, params = "deleteFile")
    public ModelAndView deleteUserFile(@ModelAttribute("certificationForm") @Valid final CertificationForm form){
        userFileService.deleteFile(form.getFileToRemove());
        return new ModelAndView("redirect:/editCertifications");

    }

}
