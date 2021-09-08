package ar.edu.itba.paw.webapp.Forms;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class ContactForm {

    @Email
    private String email;

    @NotNull
    private String name;

    private String message;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
