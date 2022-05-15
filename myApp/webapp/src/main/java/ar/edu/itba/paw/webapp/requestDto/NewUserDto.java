package ar.edu.itba.paw.webapp.requestDto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewUserDto {
    @Email(regexp = "^$|^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", groups = {Teacher.class, Student.class})
    @NotBlank(groups = {Teacher.class, Student.class})
    private String mail;

    @NotBlank(groups = {Teacher.class, Student.class})
    @Pattern(regexp = "^$|^([A-ZÀ-ÿ-,a-z. ']+[ ]*)+$", groups = {Teacher.class, Student.class})
    private String name;

    @Size(min = 8, groups = {Teacher.class, Student.class})
    private String password;

    @Range(min = 0, max = 1, groups = {Teacher.class, Student.class})
    private Long role;

    @NotBlank(groups = {Teacher.class})
    private String description;

    @NotBlank(groups = {Teacher.class})
    private String schedule;

    public interface Teacher {
    }

    public interface Student {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}
