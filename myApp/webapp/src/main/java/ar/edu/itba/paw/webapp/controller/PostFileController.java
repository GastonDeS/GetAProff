package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.PostService;
import ar.edu.itba.paw.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

@Path("/post")
@Component
public class PostFileController {
    @Autowired
    private PostService postService;

    @GET
    @Path("/{postId}/file")
    @Produces({"application/vnd.getaproff.api.v1+json"})
    public Response getPostFile(@PathParam("postId") final Long postId) {
        Post post = postService.getFileData(postId);
        Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(post.getFile()));
        response.header("Content-Disposition", "attachment; filename=\"" + post.getFilename() + "\"" );
        return response.build();
    }
}
