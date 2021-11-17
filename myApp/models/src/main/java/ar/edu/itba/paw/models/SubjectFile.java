package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "subject_files")
public class SubjectFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_files_fileid_seq")
    @SequenceGenerator(name = "subject_files_fileid_seq", sequenceName = "subject_files_fileid_seq", allocationSize = 1)
    private Long fileId;

    @Column
    private String fileName;

    @Column
    private byte[] file;

    @ManyToOne(targetEntity = Teaches.class)
    @JoinColumns({
        @JoinColumn(name = "subjectId", referencedColumnName = "subjectId"),
        @JoinColumn(name = "subjectLevel", referencedColumnName = "level"),
        @JoinColumn(name = "userid", referencedColumnName = "userid")
    })
    private Teaches teachesInfo;

    @ManyToMany(mappedBy = "sharedFilesByTeacher")
    private List<Lecture> lecturesWithFileShared;

    SubjectFile() {
        //For Hibernate
    }

    public SubjectFile(Long fileId, String fileName, byte[] file, Teaches teachesInfo){
        this.fileName = fileName;
        this.file = file;
        this.fileId = fileId;
        this.teachesInfo = teachesInfo;
    }

    private SubjectFile(Builder builder) {
        this.fileName = builder.fileName;
        this.teachesInfo = builder.teachesInfo;
        this.fileId = builder.fileId;
        this.file = builder.file;
    }


    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Teaches getTeachesInfo() {
        return teachesInfo;
    }

    public void setTeachesInfo(Teaches teachesInfo) {
        this.teachesInfo = teachesInfo;
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
        private Teaches teachesInfo;
        private Long fileId;
        private String fileName;
        private byte[] file;

        public Builder() {
        }
        public Builder teachesInfo(Teaches teachesInfo) {
            this.teachesInfo = teachesInfo;
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

        public SubjectFile build() {
            return new SubjectFile(this);
        }
    }
}

