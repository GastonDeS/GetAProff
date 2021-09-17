package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.annotations.UniqueUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, String> {

    @Autowired
    private UserService us;

    @Override
    public void initialize(UniqueUser uniqueUser) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && !us.findByEmail(email).isPresent();
    }
}
