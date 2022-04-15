package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Post;

import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;

public class PostDto {
    private String filePath, message;
    private Timestamp time;
    private Long uploader;

    public static PostDto getPostDto(UriInfo uri, Post post) {
        PostDto postDto = new PostDto();
        postDto.filePath = uri.getBaseUriBuilder().path("/files/"/*TODO id del file para saber direccionar*/).build().toString();;
        postDto.message = post.getMessage();
        postDto.time = post.getTime();
        postDto.uploader = post.getUploader().getId();
        return postDto;
    }

    public Long getUploader() {
        return uploader;
    }

    public void setUploader(Long uploader) {
        this.uploader = uploader;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
