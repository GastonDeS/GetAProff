package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.UserFile;

import javax.ws.rs.core.UriInfo;

public class UserFileDto {

    private Long fileId;

    private String name, url;

    public static UserFileDto fromUser(UriInfo uri, UserFile file) {
        UserFileDto userFileDto = new UserFileDto();
        userFileDto.url = uri.getBaseUriBuilder().path("files/user/").path(String.valueOf(file.getFileId())).build().toString();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
