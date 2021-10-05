package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

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
        int role = rf.getUserRole();
        String mail = rf.getMail();
        String pass = rf.getPassword();
        String confPass = rf.getConfirmPass();
        MultipartFile imageFile = rf.getImageFile();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "form.field.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "form.field.empty");
        if (role == 1) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "form.field.empty");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schedule", "form.field.empty");
        }

        if (us.findByEmail(mail).isPresent() && !mail.isEmpty()) {
            errors.rejectValue("mail", "form.unique.user");
        }

        if (!mail.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") && !mail.isEmpty()) {
            errors.rejectValue("mail", "form.mail.pattern");
        }

        if (pass.length() < 8) {
            errors.rejectValue("password", "form.password.size");
        }

        if (!pass.equals(confPass)) {
            errors.rejectValue("confirmPass", "form.passwords.missMatch");
        }

        if (imageFile == null || imageFile.isEmpty()) {
            if (role == 1) {
                System.out.println("entro en image file");
                errors.rejectValue("imageFile", "form.image.required");
            }
        }
//        else if (!checkContentType(imageFile.getContentType().toLowerCase())) {
//            errors.rejectValue("imageFile", "form.image.format");
//        }
    }

    private boolean checkContentType(String contentType) {
        return contentType.equals("image/jpg") || contentType.equals("image/jpeg")
                || contentType.equals("image/png");
    }
}
