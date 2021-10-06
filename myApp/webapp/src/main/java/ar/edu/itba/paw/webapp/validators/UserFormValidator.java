package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.forms.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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
        UserForm uf = (UserForm) o;
        MultipartFile imageFile = uf.getImageFile();
        String description = uf.getDescription();
        String schedule = uf.getSchedule();
        Boolean hasImage = uf.getHasImage();

        if (description.isEmpty()) {
            errors.rejectValue("description", "form.field.empty");
        }

        if (schedule.isEmpty()) {
            errors.rejectValue("schedule", "form.field.empty");
        }

        if (!hasImage) {
            errors.rejectValue("imageFile", "form.image.required");
        } else {
            if (imageFile != null || !imageFile.isEmpty()) {
                if(!checkContentType(imageFile.getContentType().toLowerCase())) {
                    errors.rejectValue("imageFile", "form.image.format");
                }
            }
        }
    }

    private boolean checkContentType(String contentType) {
        return contentType.equals("image/jpg") || contentType.equals("image/jpeg")
                || contentType.equals("image/png");
    }
}
