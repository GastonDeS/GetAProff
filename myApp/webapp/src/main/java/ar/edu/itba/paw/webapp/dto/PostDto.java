package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Post;

import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;

public class PostDto {
    private String message;
    private Timestamp time;
    private Long uploader;
    private PaginatedFileDto file;

    public static PostDto getPostDto(UriInfo uri, Post post) {
        PostDto postDto = new PostDto();
        if (post.getFilename() == null || post.getFilename().isEmpty())
            postDto.file = null;
        else
            postDto.file = PaginatedFileDto.getPaginatedFileDto(uri, "posts", "/file/", post.getFilename(), post.getPostId());
        postDto.message = post.getMessage();
        postDto.time = post.getTime();
        postDto.uploader = post.getUploader().getId();
        return postDto;
    }

    public PaginatedFileDto getFile() {
        return file;
    }

    public void setFile(PaginatedFileDto file) {
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
