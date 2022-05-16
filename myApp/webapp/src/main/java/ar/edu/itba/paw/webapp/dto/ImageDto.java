package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class ImageDto {

    private String url;

    private byte[] image;

    public static ImageDto fromUser(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.image = image.getImage();
        //imageDto.url = uri.getBaseUriBuilder().path("images/").path(String.valueOf(image.getUserid())).build().toString();
        imageDto.url = JaxRsLinkBuilder.linkTo(UsersController.class).slash(image.getUserid()).slash("images").toString();
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
