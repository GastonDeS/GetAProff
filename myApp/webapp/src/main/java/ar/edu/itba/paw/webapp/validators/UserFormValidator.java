package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.forms.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UserFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm uf = (UserForm) o;
        MultipartFile imageFile = uf.getImageFile();
        String description = uf.getDescription();
        String schedule = uf.getSchedule();

        if (description.isEmpty()) {
            errors.rejectValue("description", "form.field.empty");
        }

        if (schedule.isEmpty()) {
            errors.rejectValue("schedule", "form.field.empty");
        }

        if (imageFile == null || imageFile.isEmpty()) {
            errors.rejectValue("imageFile", "form.image.required");
        }
        else {
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
