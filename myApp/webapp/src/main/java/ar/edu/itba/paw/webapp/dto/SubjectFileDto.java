package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.webapp.controller.SubjectFilesController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

public class SubjectFileDto {

    private Long id;

    private int level;

    private String name;

    private SubjectDto subject;

    private Link url;

    public static SubjectFileDto fromSubjectFile(SubjectFile subjectFile) {
        SubjectFileDto subjectFileDto = new SubjectFileDto();
        subjectFileDto.id = subjectFile.getFileId();
        subjectFileDto.name = subjectFile.getFileName();
        subjectFileDto.subject = SubjectDto.get(subjectFile.getTeachesInfo().getSubject(), true);
        subjectFileDto.level = subjectFile.getTeachesInfo().getLevel();
        subjectFileDto.url = JaxRsLinkBuilder.linkTo(SubjectFilesController.class).slash(subjectFile.getFileId()).withRel(subjectFile.getFileId().toString());
        return subjectFileDto;
    }

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
        this.url = url;
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

//    public byte[] getFile() {
//        return file;
//    }
//
//    public void setFile(byte[] file) {
//        this.file = file;
//    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }
}
