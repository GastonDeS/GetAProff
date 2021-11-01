package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.sql.Array;
import java.util.Arrays;

@Entity
@Table(name = "user_file")
@IdClass(UserFileId.class)
public class UserFile
{
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    private User fileOwner;

    @Id
    private int fileId;

    private String fileName;

    private byte[] file;

    UserFile() {
        //For Hibernate
    }

    public UserFile(User fileOwner, String fileName, byte[] file ){
        this.fileOwner = fileOwner;
        this.fileId = Arrays.hashCode(file);
        this.fileName = fileName;
    }

    public User getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(User fileOwner) {
        this.fileOwner = fileOwner;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
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
