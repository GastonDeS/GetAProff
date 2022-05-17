package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

public class ImageDto {

    private Link url;

    private byte[] image;

    // TODO i think this doesnt need url because it points to itself
    public static ImageDto fromUser(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.image = image.getImage();
        imageDto.url = JaxRsLinkBuilder.linkTo(UsersController.class).slash(image.getUserid()).slash("images").withSelfRel();
        return imageDto;
    }

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
