package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SubjectFileService;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.webapp.dto.SubjectFileDto;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("subject-files")
@Component
public class SubjectFilesController {

    @Autowired
    private SubjectFileService subjectFileService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUserSubjectFiles(@PathParam("id") Long id) {
        final List<SubjectFileDto> subjectFileDtos = subjectFileService.getAllSubjectFilesFromUser(id).stream()
                .map(subjectFile -> SubjectFileDto.fromUser(uriInfo, subjectFile)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectFileDto>>(subjectFileDtos){}).build();
    }

    @DELETE
    @Path("/{file}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response deleteUserSubjectFile(@PathParam("file") Long file) {
        int success = subjectFileService.deleteSubjectFile(file);
        return success == 1 ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/{id}/{subject}/{level}")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA, })
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response uploadUserSubjectFiles(@PathParam("id") Long id, @PathParam("subject") Long subject,
                                           @PathParam("level") Integer level, @FormDataParam("file") InputStream uploadedInputStream,
                                           @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        byte[] file = IOUtils.toByteArray(uploadedInputStream);
        final Optional<SubjectFile> subjectFile = subjectFileService.saveNewSubjectFile(file, fileDetail.getFileName(), id, subject, level);
        return subjectFile.isPresent() ? Response.ok(SubjectFileDto.fromUser(uriInfo, subjectFile.get())).build() :
                    Response.status(Response.Status.BAD_REQUEST).build();
    }

}
