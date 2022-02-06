package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Subject;

import javax.ws.rs.core.UriInfo;

public class SubjectDto {

    private String name;

    private Long subjectId;

    private String url;

    public static SubjectDto get(UriInfo uri, Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.name = subject.getName();
        subjectDto.subjectId = subject.getSubjectId();
        subjectDto.url = uri.getBaseUriBuilder().path("subjects").path(String.valueOf(subject.getSubjectId())).build().toString();
        return subjectDto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
