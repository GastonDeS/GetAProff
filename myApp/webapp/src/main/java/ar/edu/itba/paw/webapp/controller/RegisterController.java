package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Roles;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.webapp.exceptions.RegisterErrorException;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

@Controller
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("register") final RegisterForm form) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = "teacher")
    public ModelAndView registerTeacher(@ModelAttribute("register") @Validated(RegisterForm.Teacher.class) final RegisterForm form, final BindingResult errors) {
        if(form.getImageFile().isEmpty()) errors.rejectValue("imageFile", "form.image.required");
        if(!form.getPassword().equals(form.getConfirmPass())) errors.rejectValue("confirmPass", "password.not.matching");
        if (userService.findByEmail(form.getMail()).isPresent()) errors.rejectValue("mail", "form.email.already.exists");
        if (errors.hasErrors()) {
            return new ModelAndView("register");
        }
        Long userId;
        try {
            userId = commonRegister(form);
        } catch (Exception exception) {
            throw new RegisterErrorException("exception.register");
        }
        return new ModelAndView("redirect:/editSubjects/" + userId);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, params = "student")
    public ModelAndView registerStudent(@ModelAttribute("register") @Validated(RegisterForm.Student.class) final RegisterForm form, final BindingResult errors) {
        if(form.getImageFile().isEmpty()) errors.rejectValue("imageFile", "form.image.required");
        if(!form.getPassword().equals(form.getConfirmPass())) errors.rejectValue("confirmPass", "password.not.matching");
        if (userService.findByEmail(form.getMail()).isPresent()) errors.rejectValue("mail", "form.email.already.exists");
        if (errors.hasErrors()) {
            return new ModelAndView("register");
        }
        Long userId;
        try {
            userId = commonRegister(form);
        } catch (Exception exception) {
            throw new RegisterErrorException("exception.register");
        }
        String redirect = "redirect:/profile/" + userId;
        return new ModelAndView(redirect);
    }

    private Long commonRegister(final RegisterForm form) throws IOException {
        Optional<User> maybeUser = userService.create(form.getName(), form.getMail(), form.getPassword(), form.getDescription(), form.getSchedule(), form.getUserRole());
        if (!maybeUser.isPresent()) {
            throw new RegisterErrorException("exception.register");
        }
        User user = maybeUser.get();
        imageService.createOrUpdate(user.getId(), form.getImageFile().getBytes());
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getMail());
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        LOGGER.debug("Registered user is {}", user.getId());

        return user.getId();
    }
}
