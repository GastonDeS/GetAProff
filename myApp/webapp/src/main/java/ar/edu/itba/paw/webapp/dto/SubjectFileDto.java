package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.SubjectFile;

import javax.ws.rs.core.UriInfo;

public class SubjectFileDto {

    private Long id;

    private int level;

    private String name;

    private SubjectDto subject;

    private byte[] file;

    public static SubjectFileDto fromUser(UriInfo uri, SubjectFile subjectFile) {
        SubjectFileDto subjectFileDto = new SubjectFileDto();
        subjectFileDto.id = subjectFile.getFileId();
        subjectFileDto.file = subjectFile.getFile();
        subjectFileDto.name = subjectFile.getFileName();
        subjectFileDto.subject = SubjectDto.get(uri, subjectFile.getTeachesInfo().getSubject());
        subjectFileDto.level = subjectFile.getTeachesInfo().getLevel();
        return subjectFileDto;
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

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
