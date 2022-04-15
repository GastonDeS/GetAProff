package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.SubjectFile;

import javax.ws.rs.core.UriInfo;

public class PaginatedFileDto {

    private String uri, name;

    public static PaginatedFileDto getPaginatedFileDto(UriInfo uri, SubjectFile subjectFile) {
        PaginatedFileDto paginatedFileDto = new PaginatedFileDto();
        paginatedFileDto.name = subjectFile.getFileName();
        paginatedFileDto.uri = uri.getBaseUriBuilder().path("/files/"+subjectFile.getFileId()).build().toString();
        return paginatedFileDto;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
