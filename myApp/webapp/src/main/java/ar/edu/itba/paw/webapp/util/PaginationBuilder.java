package ar.edu.itba.paw.webapp.util;

import ar.edu.itba.paw.models.Page;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PaginationBuilder {
    private PaginationBuilder() {
        // Avoid instantiation of util class
    }

    public static <T> Response build(Page<T> content,
                                     Response.ResponseBuilder builder,
                                     UriInfo uriInfo,
                                     Integer pageSize) {
        builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).queryParam("pageSize", pageSize).build().toString(), "first");

        if (content.getTotal() > 0)
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", content.getTotal()).queryParam("pageSize", pageSize).build().toString(), "last");

        if(content.getPage() < content.getTotal()){
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page",content.getPage() + 1).queryParam("pageSize", pageSize).build().toString(), "next");
        }

        if(content.getPage() > 1) {
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", content.getPage() - 1).queryParam("pageSize", pageSize).build().toString(), "prev");
        }

        builder.header("X-Total-Pages", content.getTotal());
        return builder.build();
    }
}
