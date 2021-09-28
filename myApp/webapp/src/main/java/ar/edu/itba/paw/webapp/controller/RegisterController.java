package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.UtilsService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import ar.edu.itba.paw.webapp.validators.RegisterFormValidator;
import ar.edu.itba.paw.webapp.validators.SubjectsFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterFormValidator registerFormValidator;

    @Autowired
    private SubjectsFormValidator subjectsFormValidator;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private ImageService imageService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target != null) {
            if (target.getClass().equals(RegisterForm.class)) {
                webDataBinder.setValidator(registerFormValidator);
            } else if (target.getClass().equals(SubjectsForm.class)) {
                webDataBinder.setValidator(subjectsFormValidator);
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
            return register(form);
        }
        //TODO: validar bien
        String name = utilsService.capitalizeFirstLetter(form.getName());
        User user = userService.create(name, form.getMail(), form.getPassword(), form.getUserRole()).orElse(null);
        if (form.getUserRole() == 1 && user != null) {
            String redirect = "redirect:/profile/" + user.getId();
            return new ModelAndView(redirect);
        }
        return new ModelAndView("index");
    }
}
