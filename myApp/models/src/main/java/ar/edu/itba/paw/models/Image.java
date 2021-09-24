package ar.edu.itba.paw.models;

public class Image {

    private int uid;

    private byte[] image;
    private int imgLength;
    private String photoType;

    public Image(int uid, int imgLength, byte[] image, String photoType) {
        this.uid = uid;
        this.image = image;
        this.imgLength = imgLength;
        this.photoType = photoType;
    }

    public int getUid() {
        return uid;
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

    public int getImgLength() {
        return imgLength;
    }

    public void setImgLength(int imgLength) {
        this.imgLength = imgLength;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }
}
