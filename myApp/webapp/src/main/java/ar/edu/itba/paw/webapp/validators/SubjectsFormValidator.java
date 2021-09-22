package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.forms.SubjectsForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SubjectsFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == SubjectsForm.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        SubjectsForm sf = (SubjectsForm) o;
        int price = sf.getPrice();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "form.field.empty");

        if (price < 0 || price > 2400) {
            errors.rejectValue("price", "form.price.invalid");
        }
    }
}
