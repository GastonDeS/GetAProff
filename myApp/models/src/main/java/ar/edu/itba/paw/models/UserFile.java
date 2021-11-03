package ar.edu.itba.paw.models;


import javax.persistence.*;

@Entity
@Table(name = "user_file")
public class UserFile
{

    @ManyToOne
    @JoinColumn(name = "userid")
    private User fileOwner;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_file_fileid_seq")
    @SequenceGenerator(name = "user_file_fileid_seq", sequenceName = "user_file_fileid_seq", allocationSize = 1)
    private Long fileId;

    @Column
    private String fileName;

    @Column
    private byte[] file;

    UserFile() {
        //For Hibernate
    }

    public UserFile(User fileOwner, Long fileId, String fileName, byte[] file){
        this.fileOwner = fileOwner;
        this.fileName = fileName;
        this.file = file;
        this.fileId = fileId;
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
