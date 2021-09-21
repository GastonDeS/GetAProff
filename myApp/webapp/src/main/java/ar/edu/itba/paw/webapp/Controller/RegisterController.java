package ar.edu.itba.paw.webapp.Controller;

import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.TeachesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Teaches;
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

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    Validator registerFormValidator;

    @Autowired
    TeachesService teachesService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        Object target = webDataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == RegisterForm.class) {
            webDataBinder.setValidator(registerFormValidator);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("register") final RegisterForm form) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("register") @Valid final RegisterForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return register(form);
        }
        userService.create(form.getName(), form.getMail(), form.getPassword(), form.getUserRole());

        if (form.getUserRole() == 1) {
            return subjectsForm(new SubjectsForm());
        }
        return new ModelAndView("index");
    }

    // Get subject form
    @RequestMapping(value = "/subjectsForm", method = RequestMethod.GET)
    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
        List<Teaches> teachesList;
        if (userService.getCurrentUser().isPresent()) {
            int uid = userService.getCurrentUser().get().getId();
            teachesList = teachesService.getSubjectListByUser(uid);
            return new ModelAndView("subjectsForm").addObject("subjects", teachesList);
        }
        return new ModelAndView("index");
    }

    // Save subject form
    @RequestMapping(value = "/subjectsForm", method = RequestMethod.POST)
    public ModelAndView subjectsForm (@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return subjectsForm(form);
        }
        int uid = userService.getCurrentUser().get().getId();
        int sid = subjectService.create(form.getName()).getId();
        teachesService.addSubjectToUser(uid, sid, form.getPrice(), form.getLevel());
        return new ModelAndView("subjectsForm");
    }

    public Validator getRegisterFormValidator() {
        return registerFormValidator;
    }

    public void setRegisterFormValidator(RegisterFormValidator registerFormValidator) {
        this.registerFormValidator = registerFormValidator;
    }
}
