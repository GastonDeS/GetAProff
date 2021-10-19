package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    private Long uid;

    @Column
    private byte[] image;

    public Image(Long uid, byte[] image) {
        this.uid = uid;
        this.image = image;
    }

    /*default*/ Image() {

    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
