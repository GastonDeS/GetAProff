package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterFormValidator implements Validator {

    @Autowired
    private UserService us;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == RegisterForm.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegisterForm rf = (RegisterForm) o;
        String mail = rf.getMail();
        String pass = rf.getPassword();
        String confPass = rf.getConfirmPass();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "form.field.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "form.field.empty");

        if (us.findByEmail(mail).isPresent() && !mail.isEmpty()) {
            errors.rejectValue("mail", "form.unique.user");
        }

        if (!mail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") && !mail.isEmpty()) {
            errors.rejectValue("mail", "form.mail.pattern");
        }

        if (pass.length() < 8) {
            errors.rejectValue("password", "form.password.size");
        }

        if (!pass.equals(confPass)) {
            errors.rejectValue("confirmPass", "form.passwords.missMatch");
        }
    }
}
