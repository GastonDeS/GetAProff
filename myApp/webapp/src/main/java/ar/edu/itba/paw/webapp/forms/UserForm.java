package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class UserForm {

    @NotBlank(groups = {Teacher.class})
    private String description;

    @NotBlank(groups = {Teacher.class})
    private String schedule;

    @NotBlank(groups = {Student.class, Teacher.class})
    private String name;

    private MultipartFile imageFile;

    public interface Teacher {}

    public interface Student {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
