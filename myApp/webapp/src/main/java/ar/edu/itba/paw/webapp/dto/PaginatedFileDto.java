package ar.edu.itba.paw.webapp.dto;

import javax.ws.rs.core.UriInfo;

public class PaginatedFileDto {

    private String uri, name;
    private Long id;

    public static PaginatedFileDto getPaginatedFileDto(UriInfo uri, String prefix, String postfix, String name, Long id) {
        PaginatedFileDto paginatedFileDto = new PaginatedFileDto();
        paginatedFileDto.name = name;
        paginatedFileDto.id = id;
        paginatedFileDto.uri = uri.getBaseUriBuilder().path("/"+prefix+"/"+id.toString()).path(postfix).build().toString();
        return paginatedFileDto;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
