package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserFileService;
import ar.edu.itba.paw.models.UserFile;
import ar.edu.itba.paw.webapp.dto.FileDto;
import ar.edu.itba.paw.webapp.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.util.NotFoundStatusMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("files")
@Controller
public class FilesController {

    @Autowired
    UserFileService userFileService;

    @Context
    private UriInfo uriInfo;

    //TODO: NO SE USA CREO
    @GET
    @Path("/user/{id}")
    public Response getUserFile(@PathParam("id") Long id) {
        final UserFile userFile = userFileService.getFileById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CERTIFICATION));
        return Response.ok(FileDto.fromUserFile(userFile)).build();
    }
}
