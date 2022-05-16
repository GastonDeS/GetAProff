package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.webapp.controller.PostFileController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;

public class PostDto {
    private String message;
    private Timestamp time;
    private Long uploader;
    private Link file;

    public static PostDto getPostDto(Post post) {
        PostDto postDto = new PostDto();
        if (post.getFilename() == null || post.getFilename().isEmpty())
            postDto.file = null;
        else
            postDto.file  = JaxRsLinkBuilder.linkTo(PostFileController.class).slash(post.getPostId()).slash("file").withRel(post.getFilename());
        postDto.message = post.getMessage();
        postDto.time = post.getTime();
        postDto.uploader = post.getUploader().getId();
        return postDto;
    }

    public Link getFile() {
        return file;
    }

    public void setFile(Link file) {
        this.file = file;
    }

    public Long getUploader() {
        return uploader;
    }

    public void setUploader(Long uploader) {
        this.uploader = uploader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
