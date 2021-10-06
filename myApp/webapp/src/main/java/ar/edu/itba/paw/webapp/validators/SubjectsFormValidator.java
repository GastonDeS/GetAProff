package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SubjectsFormValidator implements Validator {

    @Autowired
    private TeachesService ts;

    @Autowired
    private UserService us;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == SubjectsForm.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        SubjectsForm sf = (SubjectsForm) o;
        int price = sf.getPrice();

        if (ts.findByUserAndSubjectAndLevel(1, sf.getSubjectid(), sf.getLevel()) != null) {
            errors.rejectValue("level","form.level.invalid");
        }

        if (price <= 0 ) {
            errors.rejectValue("price", "form.price.invalid");
        }
    }
}
