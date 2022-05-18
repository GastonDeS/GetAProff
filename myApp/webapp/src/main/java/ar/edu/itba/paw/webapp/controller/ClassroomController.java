package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.webapp.dto.ClassroomDto;
import ar.edu.itba.paw.webapp.dto.ClassroomFilesDto;
import ar.edu.itba.paw.webapp.dto.IdsDto;
import ar.edu.itba.paw.webapp.dto.PostDto;
import ar.edu.itba.paw.webapp.exceptions.ConflictException;
import ar.edu.itba.paw.webapp.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.ConflictStatusMessages;
import ar.edu.itba.paw.webapp.util.NotFoundStatusMessages;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Path("/api/classroom")
@Controller
public class ClassroomController {
    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private SubjectFileService subjectFileService;

    @Autowired
    private EmailService emailService;

    @Context
    private UriInfo uriInfo;

    private final Integer SUCCESS = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomController.class);

    @GET
    @Path("/{classId}")
    @Produces(value = {"application/vnd.getaproff.api.v1+json"})
    public Response getClassroom(@PathParam("classId") final Long classId) {
        Lecture lecture = lectureService.findById(classId).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CLASS));
        lectureService.refreshTime(classId, authFacade.getCurrentUserId().equals(lecture.getStudent().getId())? 1 : 0 ); // TODO 1 student 0 teacher modificar criterio
        return Response.ok(
                ClassroomDto.getClassroom(lecture)
        ).build();
    }

    @GET
    @Path("/{classId}/posts")
    @Produces(value = {"application/vnd.getaproff.api.v1+json"})
    public Response getClassroomComments(@PathParam("classId") final Long classId,
                                         @QueryParam("page") @DefaultValue("1") Integer page,
                                         @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        final Page<Post> posts = postService.retrievePosts(classId, page, pageSize);
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<PostDto>>(posts.getContent().stream().map(PostDto::getPostDto).collect(Collectors.toList())) {
                });
        return PaginationBuilder.build(posts, builder, uriInfo, pageSize);
    }

    @GET
    @Path("/{classId}/files")
    @Produces(value = {"application/vnd.getaproff.api.v1+json"})
    public Response getClassroomFiles(@PathParam("classId") final Long classId) {
        User currUser = authFacade.getCurrentUser();
        Pair<List<SubjectFile>, List<SubjectFile>> files = lectureService.getTeacherFiles(classId, currUser.getId());
        final ClassroomFilesDto ans = ClassroomFilesDto.getClassroomFilesDto(files.getValue1(), files.getValue2());
        return Response.ok(new GenericEntity<ClassroomFilesDto>(ans){}).build();
    }

    // TODO exception
    @POST
    @Path("/{classId}/files")
    @Consumes(value = {"application/vnd.getaproff.api.v1+json"})
    public Response shareFileInLecture(@PathParam("classId") final Long classId, @Valid @RequestBody IdsDto filesId) {
        int ans = 1;
        for(Long id : filesId.getIds()) {
            ans *= lectureService.shareFileInLecture(id, classId);
        }
        //TODO: add exception here
        if(ans == 0) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.ok().build();
    }

    // TODO exception
    @DELETE
    @Path("/{classId}/files/{fileId}")
    @Produces(value = {"application/vnd.getaproff.api.v1+json"})
    public Response stopSharingFileInLecture(@PathParam("classId") final Long classId, @PathParam("fileId") Long fileId){
        int ans = lectureService.stopSharingFileInLecture(fileId, classId);
        if(ans == 0) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.ok().build();
    }

    //TODO security on this
    @POST
    @Path("/{classId}/posts")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    public Response uploadPosts(@PathParam("classId") final Long classId, @FormDataParam("message") final String message,
                                @FormDataParam("uploader") final Long uploader, @FormDataParam("file") InputStream uploadedInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        Lecture lecture = lectureService.findById(classId).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CLASS));
        if (!(lecture.getTeacher().getId().equals(uploader) || lecture.getStudent().getId().equals(uploader))) { // TODO fix this to the future handler
            return Response.status(Response.Status.FORBIDDEN).entity("The user "+uploader+" doesn't belong to this classroom").build();
        }
        if (uploadedInputStream == null || fileDetail == null) return Response.status(Response.Status.BAD_REQUEST).entity("file was empty").build();
        Post post = postService.post(uploader, classId, fileDetail.getFileName(), IOUtils.toByteArray(uploadedInputStream), message, fileDetail.getType())
                .orElseThrow(() -> new ConflictException(ConflictStatusMessages.POST)); // TODO conflict or 50X
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + post.getPostId());
        LOGGER.debug("Created post with id {} to classroom with id {}", post.getPostId(), classId);
        emailService.sendNewPostMessage(uploader, lecture, uriInfo.getBaseUri().toString());
        return Response.created(location).build();
    }

    // TODO exception for not success
    @POST
    @Path("/{classId}/{status}")
    public Response changeStatus(@PathParam("classId") final Long classId, @PathParam("status") final int status) {
        Lecture lecture = lectureService.findById(classId).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CLASS));
        int updated = lectureService.setStatus(classId, status);
        emailService.sendStatusChangeMessage(lecture, status,uriInfo.getBaseUri().toString());
        LOGGER.debug("Changed status of classroom with id {} to {}", classId, status);
        return updated == SUCCESS ? Response.status(Response.Status.ACCEPTED).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
