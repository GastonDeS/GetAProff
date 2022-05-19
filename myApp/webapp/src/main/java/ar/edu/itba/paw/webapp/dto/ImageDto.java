package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

public class ImageDto {


    private byte[] image;

    public static ImageDto fromUser(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.image = image.getImage();
        return imageDto;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
