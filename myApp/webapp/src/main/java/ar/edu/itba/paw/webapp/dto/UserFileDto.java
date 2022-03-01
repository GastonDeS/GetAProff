package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.UserFile;

import javax.ws.rs.core.UriInfo;

public class UserFileDto {

    private Long id;

    private String name, url;

    private byte[] file;

    public static UserFileDto fromUser(UriInfo uri, UserFile file) {
        UserFileDto userFileDto = new UserFileDto();
        userFileDto.url = uri.getBaseUriBuilder().path("files/user/").path(String.valueOf(file.getFileId())).build().toString();
        userFileDto.id = file.getFileId();
        userFileDto.name = file.getFileName();
        userFileDto.file = file.getFile();
        return userFileDto;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
