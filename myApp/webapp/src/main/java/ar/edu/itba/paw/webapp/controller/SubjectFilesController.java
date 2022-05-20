package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SubjectFileService;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.FileDto;
import ar.edu.itba.paw.webapp.dto.SubjectFileDto;
import ar.edu.itba.paw.webapp.exceptions.NoContentException;
import ar.edu.itba.paw.webapp.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.NoContentStatusMessages;
import ar.edu.itba.paw.webapp.util.NotFoundStatusMessages;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/subject-files")
@Controller
public class SubjectFilesController {

    @Autowired
    private SubjectFileService subjectFileService;

    @Autowired
    private AuthFacade authFacade;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectFilesController.class);

    @GET
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getUserSubjectFiles() {
        Long uid = authFacade.getCurrentUserId();
        final List<SubjectFileDto> subjectFileDtos = subjectFileService.getAllSubjectFilesFromUser(uid).stream()
                .map(SubjectFileDto::fromSubjectFile).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectFileDto>>(subjectFileDtos){}).build();
    }

    @GET
    @Path("/{fileId}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getUserSubjectFile(@PathParam("fileId") Long fileId) {
        SubjectFile subjectFile = subjectFileService.getSubjectFileById(fileId)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.SUBJECT_FILE));
        FileDto fileDto = FileDto.fromSubjectFile(subjectFile);
        return Response.ok(fileDto).build();
    }

    @DELETE
    @Path("/{file}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response deleteUserSubjectFile(@PathParam("file") Long file) {
        int success = subjectFileService.deleteSubjectFile(file);
        if (success == 0) throw new NoContentException(NoContentStatusMessages.SUBJECT_FILE);
        return Response.ok().build();
    }
    
    @POST
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA, })
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response uploadUserSubjectFiles(@FormDataParam("subject") Long subject,
                                           @FormDataParam("level") Integer level, @FormDataParam("file") InputStream uploadedInputStream,
                                           @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        byte[] file = IOUtils.toByteArray(uploadedInputStream);
        User currUser = authFacade.getCurrentUser();
        final SubjectFile subjectFile = subjectFileService.saveNewSubjectFile(file, fileDetail.getFileName(), currUser.getId(), subject, level)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.SUBJECT_FILE));
        URI location = URI.create(uriInfo.getBaseUri()+"/"+subjectFile.getFileId());
        LOGGER.debug("Subject file uploaded successfully");
        return Response.created(location).build();
    }
}
