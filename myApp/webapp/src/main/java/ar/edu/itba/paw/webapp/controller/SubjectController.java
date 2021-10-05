package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.AcceptForm;
import ar.edu.itba.paw.webapp.forms.NewSubjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SubjectController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/newSubjectForm/{uid}", method = RequestMethod.GET)
    public ModelAndView newSubjectForm(@ModelAttribute("newSubjectForm") final NewSubjectForm form, @PathVariable("uid") final int uid) {
        final ModelAndView mav = new ModelAndView("newSubjectForm");
        return mav;
    }

    @RequestMapping(value = "/newSubjectForm/{uid}", method = RequestMethod.POST)
    public ModelAndView newSubejct(@PathVariable("uid") final int uid, @ModelAttribute("newSubjectForm") @Valid final NewSubjectForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return newSubjectForm(form, uid);
        }
        return new ModelAndView("redirect:/profile/" + String.valueOf(uid));
    }
}
