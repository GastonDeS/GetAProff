package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class ContactForm {

    private String message;

    @NotNull
    private Integer subjectId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubjectId(Integer subject) {
        this.subjectId = subject;
    }

    public Integer getSubjectId() {
        return subjectId;
    }
}
