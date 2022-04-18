package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/post")
@Component
public class PostFileController {
    @Autowired
    private PostService postService;

    @GET
    @Path("/{postId}/file")
    @Produces({MediaType.IMAGE_JPEG_VALUE}) //TODO see how to specify a generic file
    public Response getPostFile(@PathParam("postId") final Long postId) {
        return Response.ok(postService.getFileData(postId).getFile()).build();
    }
}
