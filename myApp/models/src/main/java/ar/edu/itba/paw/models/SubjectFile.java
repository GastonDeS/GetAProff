package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "subject_files")
public class SubjectFile {

    @ManyToOne
    @JoinColumn(name = "userid")
    private User fileOwner;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_files_fileid_seq")
    @SequenceGenerator(name = "subject_files_fileid_seq", sequenceName = "subject_files_fileid_seq", allocationSize = 1)
    private Long fileId;

    @Column
    private String fileName;

    @Column
    private byte[] file;

    @Column
    private Integer level;

    @ManyToOne
    @JoinColumn(name = "subjectid")
    private Subject subject;

    @ManyToMany(mappedBy = "sharedFilesByTeacher")
    private List<Lecture> lecturesWithFileShared;

    SubjectFile() {
        //For Hibernate
    }

    public SubjectFile(User fileOwner, Long fileId, String fileName, byte[] file, Integer level, Subject subject){
        this.fileOwner = fileOwner;
        this.fileName = fileName;
        this.file = file;
        this.fileId = fileId;
        this.level = level;
        this.subject = subject;
    }

    public User getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(User fileOwner) {
        this.fileOwner = fileOwner;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}

