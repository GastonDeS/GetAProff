package ar.edu.itba.paw.webapp.forms;

import org.springframework.web.multipart.MultipartFile;

public class UserForm {

    private String description, schedule;

    private MultipartFile imageFile;

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
