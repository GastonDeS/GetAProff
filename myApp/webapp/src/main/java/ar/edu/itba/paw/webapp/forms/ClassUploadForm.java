package ar.edu.itba.paw.webapp.forms;

import org.springframework.web.multipart.MultipartFile;

public class ClassUploadForm {

    private String message;

    private MultipartFile file;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
