package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class LinkUserDto {
    private String user;

    public static LinkUserDto fromUserId(String userId) {
        LinkUserDto linkUserDto = new LinkUserDto();
        //linkUserDto.user = uri.getBaseUriBuilder().path("/api/users/"+userId).build().toString();
        linkUserDto.user = JaxRsLinkBuilder.linkTo(UsersController.class).slash(userId).withSelfRel().toString();
        return linkUserDto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
