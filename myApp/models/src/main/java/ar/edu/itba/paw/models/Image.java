package ar.edu.itba.paw.models;

public class Image {

    private int uid;

    public int getUid() {
        return uid;
    }

    public Image(int uid, byte[] image) {
        this.uid = uid;
        this.image = image;
    }

    public void setUid(int uid) {
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
