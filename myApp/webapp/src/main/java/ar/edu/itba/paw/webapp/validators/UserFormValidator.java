package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.forms.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class UserFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm userForm = (UserForm) o;
        MultipartFile imageFile = userForm.getImageFile();
        String description = userForm.getDescription();
        String schedule = userForm.getSchedule();
        String name = userForm.getName();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "form.field.empty");

        if (!name.isEmpty() && !name.matches("^([A-ZÀ-ÿ-,a-z. ']+[ ]*)+$")) {
            errors.rejectValue("name", "form.name.format");
        }

        if(userForm.isTeacher()) {
            if (description.isEmpty()) {
                errors.rejectValue("description", "form.field.empty");
            }

            if (schedule.isEmpty()) {
                errors.rejectValue("schedule", "form.field.empty");
            }
        }

        if (imageFile != null && imageFile.getSize() > 0) {
            if(!checkContentType(imageFile.getContentType().toLowerCase())) {
                errors.rejectValue("imageFile", "form.image.format");
            }
        }
    }

    private boolean checkContentType(String contentType) {
        return contentType.equals("image/jpg") || contentType.equals("image/jpeg")
                || contentType.equals("image/png");
    }
}
