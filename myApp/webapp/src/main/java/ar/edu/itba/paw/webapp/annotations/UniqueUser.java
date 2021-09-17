package ar.edu.itba.paw.webapp.annotations;
import ar.edu.itba.paw.webapp.validators.UniqueUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UniqueUserValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface UniqueUser {

    String message() default "There is already user with this email address!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};
}
