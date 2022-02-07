package ar.edu.itba.paw.webapp.requestDto;

import ar.edu.itba.paw.webapp.forms.RegisterForm;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterDto {
    @Email(regexp = "^$|^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", groups = {RegisterForm.Teacher.class, RegisterForm.Student.class})
    @NotBlank(groups = {RegisterForm.Teacher.class, RegisterForm.Student.class})
    private String mail;

    @NotBlank(groups = {RegisterForm.Teacher.class, RegisterForm.Student.class})
    @Pattern(regexp = "^$|^([A-ZÀ-ÿ-,a-z. ']+[ ]*)+$", groups = {RegisterForm.Teacher.class, RegisterForm.Student.class})
    private String name;

    @Size(min = 8, groups = {RegisterForm.Teacher.class, RegisterForm.Student.class})
    private String password;

    private String confirmPass;

    private Long userRole;

    @NotBlank(groups = {RegisterForm.Teacher.class})
    private String description;

    @NotBlank(groups = {RegisterForm.Teacher.class})
    private String schedule;

    private MultipartFile imageFile;

    public interface Teacher {}

    public interface Student {}

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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
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

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public Long getUserRole() {
        return userRole;
    }

    public void setUserRole(Long userRole) {
        this.userRole = userRole;
    }
}
