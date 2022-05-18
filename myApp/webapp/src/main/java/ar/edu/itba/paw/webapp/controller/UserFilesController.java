package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserFileService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserFile;
import ar.edu.itba.paw.webapp.dto.FileDto;
import ar.edu.itba.paw.webapp.dto.UserFileDto;
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
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/user-files")
@Controller
public class UserFilesController {

    @Autowired
    UserFileService userFileService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private UserService userService;

    private final static Logger LOGGER = LoggerFactory.getLogger(UserFilesController.class);

    @GET
    public Response getAllFilesFromUser(@QueryParam("id") Long id) {
        final User user = userService.findById(id).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.USER));
        final List<UserFileDto> userFileDtos = userFileService.getAllUserFiles(user.getId()).stream()
                .map(UserFileDto::fromUser).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<UserFileDto>>(userFileDtos){}).build();
    }

    @GET
    @Path("/{fileId}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getUserFile(@PathParam("fileId") Long fileId) {
        final UserFile userFile = userFileService.getFileById(fileId)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CERTIFICATION));
        return Response.ok(FileDto.fromUserFile(userFile)).build();
    }

    @DELETE
    @Path("/{fileId}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response deleteUserCertificationFile(@PathParam("fileId") Long fileId) {
        int success = userFileService.deleteFile(fileId);
        if (success == 0) throw new NoContentException(NoContentStatusMessages.CERTIFICATION);
        return Response.ok().build();
    }

    @POST
    @Path("/{uid}")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA, })
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response uploadUserFiles(@PathParam("uid") Long uid, @FormDataParam("file") InputStream uploadedInputStream,
                                    @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        byte[] file = IOUtils.toByteArray(uploadedInputStream);
        final UserFile userFile = userFileService.saveNewFile(file, fileDetail.getFileName(), uid)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CERTIFICATION));
        LOGGER.debug("File uploaded successfully");
        return Response.ok(UserFileDto.fromUser(userFile)).build();
    }

}
