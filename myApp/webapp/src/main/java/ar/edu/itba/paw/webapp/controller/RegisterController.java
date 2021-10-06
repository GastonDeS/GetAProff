package ar.edu.itba.paw.webapp.controller;

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
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterFormValidator registerFormValidator;

    @Autowired
    private UserDetailsService userDetailsService;

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
    public ModelAndView register(@ModelAttribute("register") @Valid final RegisterForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("register");
        }
        Optional<User> maybeUser = userService.create(form.getName(), form.getMail(), form.getPassword(), form.getDescription(), form.getSchedule(), form.getUserRole());
        if (!maybeUser.isPresent()) {
            throw new RegisterErrorException("exception.register"); //mandar a register con msj de error
        }
        User u = maybeUser.get();
        UserDetails user = userDetailsService.loadUserByUsername(u.getMail());
        Authentication auth = new UsernamePasswordAuthenticationToken(u.getMail(), u.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (form.getUserRole() == 1) {
            return new ModelAndView("redirect:/editSubjects");
        }
        String redirect = "redirect:/profile/" + maybeUser.get().getId();
        return new ModelAndView(redirect);
    }
}
