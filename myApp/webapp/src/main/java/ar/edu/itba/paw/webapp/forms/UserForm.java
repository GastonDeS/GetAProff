package ar.edu.itba.paw.webapp.forms;

import org.springframework.web.multipart.MultipartFile;

public class UserForm {
    private String description;

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
}
