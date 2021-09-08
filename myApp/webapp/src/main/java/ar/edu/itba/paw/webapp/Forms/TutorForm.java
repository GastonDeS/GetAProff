package ar.edu.itba.paw.webapp.Forms;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class TutorForm {

    @Email
    private String mail;

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
