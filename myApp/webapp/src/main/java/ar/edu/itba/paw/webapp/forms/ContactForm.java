package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.ClassInfo;
import ar.edu.itba.paw.models.Teaches;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactForm {

    @Size(max = 256)
    private String message;

    @NotEmpty
    private String subjectAndLevel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubjectAndLevel() {
        return subjectAndLevel;
    }

    public void setSubjectAndLevel(String subjectAndLevel) {
        this.subjectAndLevel = subjectAndLevel;
    }
}
