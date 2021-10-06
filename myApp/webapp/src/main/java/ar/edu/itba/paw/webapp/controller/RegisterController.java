package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.RegisterErrorException;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import ar.edu.itba.paw.webapp.validators.RegisterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterFormValidator registerFormValidator;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ImageService imageService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target != null) {
            if (target.getClass().equals(RegisterForm.class)) {
                webDataBinder.setValidator(registerFormValidator);
            }
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("register") final RegisterForm form) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("register") @Valid final RegisterForm form, final BindingResult errors) throws IOException {
        if (errors.hasErrors()) {
            return new ModelAndView("register");
        }
        Optional<User> maybeUser = userService.create(form.getName(), form.getMail(), form.getPassword(), form.getDescription(), form.getSchedule(), form.getUserRole());
        if (!maybeUser.isPresent()) {
            throw new RegisterErrorException("exception.register");
        }
        User user = maybeUser.get();
        imageService.createOrUpdate(user.getId(), form.getImageFile().getBytes());
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getMail());
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (form.getUserRole() == 1) {
            return new ModelAndView("redirect:/editSubjects");
        }
        String redirect = "redirect:/profile/" + maybeUser.get().getId();
        return new ModelAndView(redirect);
    }
}
