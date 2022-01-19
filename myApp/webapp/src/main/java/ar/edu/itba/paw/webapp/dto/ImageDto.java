package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Image;

import javax.ws.rs.core.UriInfo;

public class ImageDto {

    private String url;

    private byte[] image;

    public static ImageDto fromUser(UriInfo uri, Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.image = image.getImage();
        imageDto.url = uri.getBaseUriBuilder().path("api/users").path(String.valueOf(image.getUserid())).build().toString();
        return imageDto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
