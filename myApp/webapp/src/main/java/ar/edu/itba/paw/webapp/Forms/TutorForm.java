package ar.edu.itba.paw.webapp.Forms;

import ar.edu.itba.paw.models.Timetable;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class TutorForm {

    @Email
    private String mail;

    @NotNull
    private String name;

    private Map<Integer,Integer> subjects;

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

    public Map<Integer,Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<Integer,Integer> subjects) {
        this.subjects = subjects;
    }

    public Timetable getUserSchedule() {
        return userSchedule;
    }

    public void setUserSchedule(Timetable userSchedule) {
        this.userSchedule = userSchedule;
    }
}
