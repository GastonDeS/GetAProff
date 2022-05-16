package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
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
import java.util.Optional;
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
        Lecture lecture = checkLectureExistence(classId);
        return Response.ok(
                ClassroomDto.getClassroom(uriInfo ,lecture)
        ).build();
    }

    @GET
    @Path("/{classId}/posts")
    @Produces(value = {"application/vnd.getaproff.api.v1+json"})
    public Response getClassroomComments(@PathParam("classId") final Long classId,
                                         @QueryParam("page") @DefaultValue("1") Integer page,
                                         @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
//        try {
            final Page<Post> posts = postService.retrievePosts(classId, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<PostDto>>(posts.getContent().stream().map(post -> PostDto.getPostDto(uriInfo, post)).collect(Collectors.toList())) {
                    });
            return PaginationBuilder.build(posts, builder, uriInfo, pageSize);
//        } catch (IllegalArgumentException exception) { //TODO mensaje exacto
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
    }

    @GET
    @Path("/{classId}/files")
    @Produces(value = {"application/vnd.getaproff.api.v1+json"})
    public Response getClassroomFiles(@PathParam("classId") final Long classId) {
        Pair<List<SubjectFile>, List<SubjectFile>> files = lectureService.getTeacherFiles(classId, 13L/*TODO id from authentication*/);
        final ClassroomFilesDto ans = ClassroomFilesDto.getClassroomFilesDto(uriInfo, files.getValue1(), files.getValue2());
        return Response.ok(new GenericEntity<ClassroomFilesDto>(ans){}).build();
    }

    @POST
    @Path("/{classId}/files")
    public Response changeFilesVisibility(@PathParam("classId") final Long classId, @Valid @RequestBody IdsDto filesId){
        for(Long id : filesId.getIds())
            lectureService.changeFileVisibility(id, classId);
        return Response.ok().build();
    }

    @POST
    @Path("/{classId}/posts")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    public Response uploadPosts(@PathParam("classId") final Long classId, @FormDataParam("message") final String message,
                                @FormDataParam("uploader") final Long uploader, @FormDataParam("file") InputStream uploadedInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        Lecture lecture = checkLectureExistence(classId);
        if (!(lecture.getTeacher().getId().equals(uploader) || lecture.getStudent().getId().equals(uploader))) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The user "+uploader+" doesn't belong to this classroom").build();
        }
        if (uploadedInputStream == null || fileDetail == null) return Response.status(Response.Status.BAD_REQUEST).entity("file was empty").build();
        Optional<Post> maybePost = postService.post(uploader, classId, fileDetail.getFileName(), IOUtils.toByteArray(uploadedInputStream), message, fileDetail.getType());
        if (!maybePost.isPresent()) throw new OperationFailedException("exception.failed");
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + maybePost.get().getPostId());
        LOGGER.debug("Created post with id {} to classroom with id {}", maybePost.get().getPostId(), classId);
        emailService.sendNewPostMessage(uploader, lecture, uriInfo.getBaseUri().toString());
        return Response.created(location).build();
    }

    @POST
    @Path("/{classId}/status")
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response changeStatus(@PathParam("classId") final Long classId, @Valid @RequestBody NewStatusDto newStatus) throws IOException{
        Lecture lecture = checkLectureExistence(classId);
        //TODO: el alumno no la puede cancelar?
//        if( newStatus.getStatus() == 4)
//            if (!Objects.equals(lecture.getTeacher().getId(), newStatus.getUserId()))
//                return Response.status(Response.Status.FORBIDDEN).build();
        int updated = lectureService.setStatus(classId, newStatus.getStatus());
        emailService.sendStatusChangeMessage(lecture, newStatus.getStatus(),uriInfo.getBaseUri().toString());
        LOGGER.debug("Changed status of classroom with id {} to {}", classId, newStatus.getStatus());
        return updated == SUCCESS ? Response.status(Response.Status.ACCEPTED).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    public Response as() throws IOException {
        return Response.ok().build();
    }

    private Lecture checkLectureExistence(Long classId) throws ClassNotFoundException {
        Optional<Lecture> maybeClass = lectureService.findById(classId);
        if (!maybeClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + classId);
        }
        return maybeClass.get();
    }
}
