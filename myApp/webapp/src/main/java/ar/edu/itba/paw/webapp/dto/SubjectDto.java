package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.controller.SubjectController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class SubjectDto {

    private String name;

    private Long subjectId;

    private Link url;

    public static SubjectDto get(Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.name = subject.getName();
        subjectDto.subjectId = subject.getSubjectId();
        subjectDto.url = JaxRsLinkBuilder.linkTo(SubjectController.class).slash(subject.getSubjectId()).withSelfRel();
        return subjectDto;
    }

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
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
