package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.PostService;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.webapp.exceptions.PostFileNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

@Path("/api/post")
@Component
public class PostFileController {
    @Autowired
    private PostService postService;

    //TODO: produces?
    @GET
    @Path("/{postId}/file")
    public Response getPostFile(@PathParam("postId") final Long postId) {
        Post post = postService.getPost(postId).orElseThrow(PostNotFoundException::new);
        if (post.getFile() == null || post.getFile().length == 0) throw new PostFileNotFoundException();
        Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(post.getFile()));
        response.header("Content-Disposition", "attachment; filename=\"" + post.getFilename() + "\"" );
        return response.build();
    }
}
