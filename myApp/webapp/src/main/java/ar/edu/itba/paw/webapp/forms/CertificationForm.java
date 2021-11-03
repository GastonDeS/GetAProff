package ar.edu.itba.paw.webapp.forms;

import org.springframework.web.multipart.MultipartFile;

public class CertificationForm {

    private MultipartFile userFileToUpload;

    public MultipartFile getUserFileToUpload() {
        return userFileToUpload;
    }

    public void setUserFileToUpload(MultipartFile userFileToUpload) {
        this.userFileToUpload = userFileToUpload;
    }

    private Long fileToRemove;

    public Long getFileToRemove() {
        return fileToRemove;
    }

    public void setFileToRemove(Long fileToRemove) {
        this.fileToRemove = fileToRemove;
    }
}
