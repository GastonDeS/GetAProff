package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SubjectsFormValidator implements Validator {

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == SubjectsForm.class;
    }

    @Override
    public void validate(Object object, Errors errors) {
        SubjectsForm subjectsForm = (SubjectsForm) object;
        int price = subjectsForm.getPrice();

        Optional<User> current = userService.getCurrentUser();
        if (!current.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }

        if (teachesService.findByUserAndSubjectAndLevel(current.get().getId(), subjectsForm.getSubjectId(), subjectsForm.getLevel()).isPresent()) {
            errors.rejectValue("level","form.level.invalid");
        }

        if (price <= 0 ) {
            errors.rejectValue("price", "form.price.invalid");
        }
    }
}
