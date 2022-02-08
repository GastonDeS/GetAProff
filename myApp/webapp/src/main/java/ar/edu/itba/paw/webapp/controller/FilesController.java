package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserFileService;
import ar.edu.itba.paw.models.UserFile;
import ar.edu.itba.paw.webapp.dto.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("files")
@Component
public class FilesController {

    @Autowired
    UserFileService userFileService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/user/{id}")
    public Response getUserFile(@QueryParam("id") Long id) {
//        final Optional<UserFile> userFile = userFileService.getFileById(id);
//        return userFile.isPresent() ? Response.ok(FileDto.fromUserFile(userFile.get())).build() : Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().build();
    }
}
