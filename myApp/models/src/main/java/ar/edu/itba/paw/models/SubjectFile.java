package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject_files")
public class SubjectFile {

    @ManyToOne
    @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "subject_files_userid_fkey"))
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
    @JoinColumn(name = "subjectid", foreignKey = @ForeignKey(name = "subject_files_subjectid_fkey"))
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

    private SubjectFile(Builder builder) {
        this.fileOwner = builder.fileOwner;
        this.fileName = builder.fileName;
        this.subject = builder.subject;
        this.level = builder.level;
        this.fileId = builder.fileId;
        this.file = builder.file;
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

    public static class Builder
    {
        private User fileOwner;
        private Subject subject;
        private Long fileId;
        private String fileName;
        private byte[] file;
        private Integer level;

        public Builder() {
        }
        public Builder fileOwner(User fileOwner) {
            this.fileOwner = fileOwner;
            return this;
        }
        public Builder subject(Subject subject) {
            this.subject = subject;
            return this;
        }
        public Builder fileId(Long fileId) {
            this.fileId = fileId;
            return this;
        }
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
        public Builder file(byte[] file) {
            this.file = file;
            return this;
        }
        public Builder level(Integer level) {
            this.level = level;
            return this;
        }
        public SubjectFile build() {
            return new SubjectFile(this);
        }
    }
}

