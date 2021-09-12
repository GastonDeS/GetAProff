package ar.edu.itba.paw.webapp.Forms;

import ar.edu.itba.paw.models.Timetable;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class TutorForm {

    @Email
    private String mail;

    @NotNull
    private String name;

    private String subject;

    private Integer price;

    private Timetable userSchedule;

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

    public Timetable getUserSchedule() {
        return userSchedule;
    }

    public void setUserSchedule(Timetable userSchedule) {
        this.userSchedule = userSchedule;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
