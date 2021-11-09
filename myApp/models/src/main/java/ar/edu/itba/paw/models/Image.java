package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    private Long userid;

    @Column
    private byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "images_userid_fkey"))
    @MapsId
    private User user;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image(Long uid, byte[] image) {
        this.userid = uid;
        this.image = image;
    }

    /*default*/ Image() {
        //Just for hibernate
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
