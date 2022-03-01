package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserFileService;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.UserFile;
import ar.edu.itba.paw.webapp.dto.SubjectFileDto;
import ar.edu.itba.paw.webapp.dto.UserFileDto;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("user-files")
@Component
public class UserFilesController {

    @Autowired
    UserFileService userFileService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    public Response getAllFilesFromUser(@PathParam("id") Long id) {
        final List<UserFileDto> userFileDtos = userFileService.getAllUserFiles(id).stream()
                .map(file -> UserFileDto.fromUser(uriInfo, file)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<UserFileDto>>(userFileDtos){}).build();
    }

    @DELETE
    @Path("/{file}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response deleteUserSubjectFile(@PathParam("file") Long file) {
        int success = userFileService.deleteFile(file);
        return success == 1 ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/{id}")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA, })
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response uploadUserSubjectFiles(@PathParam("id") Long id, @FormDataParam("file") InputStream uploadedInputStream,
                                           @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        byte[] file = IOUtils.toByteArray(uploadedInputStream);
        final Optional<UserFile> userFile = userFileService.saveNewFile(file, fileDetail.getFileName(), id);
        return userFile.isPresent() ? Response.ok(UserFileDto.fromUser(uriInfo, userFile.get())).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

}
