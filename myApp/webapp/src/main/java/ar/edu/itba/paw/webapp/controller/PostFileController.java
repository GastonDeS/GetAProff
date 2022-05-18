package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.PostService;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.webapp.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.util.NotFoundStatusMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

@Path("/api/post")
@Controller
public class PostFileController {
    @Autowired
    private PostService postService;

    @GET
    @Path("/{postId}/file")
    @Produces({"application/vnd.getaproff.api.v1+json"})
    public Response getPostFile(@PathParam("postId") final Long postId) {
        Post post = postService.getPost(postId).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.POST));
        if (post.getFile() == null || post.getFile().length == 0) throw new NotFoundException(NotFoundStatusMessages.POST_FILE);
        Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(post.getFile()));
        response.header("Content-Disposition", "attachment; filename=\"" + post.getFilename() + "\"" );
        return response.build();
    }
}
