package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.UserFile;
import ar.edu.itba.paw.webapp.controller.FilesController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class UserFileDto {

    private Long id;

    private String name;

    private Link url;

    private byte[] file;

    public static UserFileDto fromUser(UserFile file) {
        UserFileDto userFileDto = new UserFileDto();
        userFileDto.url = JaxRsLinkBuilder.linkTo(FilesController.class).slash("user").slash(file.getFileId()).withSelfRel();
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

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
        this.url = url;
    }
}
