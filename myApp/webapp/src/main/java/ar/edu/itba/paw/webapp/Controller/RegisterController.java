package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.Forms.RegisterForm;
import ar.edu.itba.paw.webapp.Forms.SubjectsForm;
import ar.edu.itba.paw.webapp.validators.RegisterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    Validator registerFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(registerFormValidator);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("register") final RegisterForm form) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("register") @Valid final RegisterForm form, final BindingResult errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return register(form);
        }
        final User u = userService.create(form.getName(), form.getMail(), form.getPassword(), form.getUserRole()).get();

        if (form.getUserRole() == 1) {
            return new ModelAndView("subjectsForm").addObject("currentUser", u);
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/register/subjectsForm", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        return new ModelAndView("subjectsForm");
    }

    @RequestMapping(value = "/register/subjectsForm")
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        return new ModelAndView("index");
    }

    public Validator getRegisterFormValidator() {
        return registerFormValidator;
    }

    public void setRegisterFormValidator(RegisterFormValidator registerFormValidator) {
        this.registerFormValidator = registerFormValidator;
    }
}
