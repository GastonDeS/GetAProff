package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class LinkUserDto {
    private Link user;

    public static LinkUserDto fromUserId(String userId) {
        LinkUserDto linkUserDto = new LinkUserDto();
        linkUserDto.user = JaxRsLinkBuilder.linkTo(UsersController.class).slash(userId).withSelfRel();
        return linkUserDto;
    }

    public Link getUser() {
        return user;
    }

    public void setUser(Link user) {
        this.user = user;
    }
}
