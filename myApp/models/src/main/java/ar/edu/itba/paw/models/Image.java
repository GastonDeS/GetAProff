package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    private Long userid;

    @Column
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "userid")
    @MapsId
    private User user;

    public Image(Long uid, byte[] image) {
        this.userid = uid;
        this.image = image;
    }

    /*default*/ Image() {

    }

    public Long getUid() {
        return userid;
    }

    public void setUid(Long uid) {
        this.userid = uid;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
