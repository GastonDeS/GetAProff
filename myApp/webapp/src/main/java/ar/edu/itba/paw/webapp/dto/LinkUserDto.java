package ar.edu.itba.paw.webapp.dto;

import javax.ws.rs.core.UriInfo;

public class LinkUserDto {
    private String user;

    public static LinkUserDto fromUserId(UriInfo uri, String userId) {
        LinkUserDto linkUserDto = new LinkUserDto();
        linkUserDto.user = uri.getBaseUriBuilder().path("/api/users/"+userId).build().toString();
        return linkUserDto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
