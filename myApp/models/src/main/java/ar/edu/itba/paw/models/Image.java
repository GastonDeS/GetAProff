package ar.edu.itba.paw.models;

public class Image {

    private Long uid;

    public Long getUid() {
        return uid;
    }

    public Image(Long uid, byte[] image) {
        this.uid = uid;
        this.image = image;
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

    private byte[] image;
}
