package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.UserFile;

public class UserFileDto {

    private Long fileId;

    private String name;

    private byte[] file;

    public static UserFileDto fromUser(UserFile file) {
        UserFileDto userFileDto = new UserFileDto();
        userFileDto.file = file.getFile();
        userFileDto.fileId = file.getFileId();
        userFileDto.name = file.getFileName();
        return userFileDto;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
