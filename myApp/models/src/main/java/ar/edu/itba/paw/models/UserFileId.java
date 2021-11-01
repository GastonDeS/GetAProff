package ar.edu.itba.paw.models;

import java.io.Serializable;

public class UserFileId implements Serializable {
    private Long fileOwner;
    private int fileId;

    UserFileId() {
        //Just for Hibernate
    }

    public Long getfileOwner() {
        return fileOwner;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileOwner(Long fileOwner) {
        this.fileOwner = fileOwner;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
