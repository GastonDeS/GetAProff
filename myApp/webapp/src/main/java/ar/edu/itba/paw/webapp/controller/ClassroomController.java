package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.interfaces.services.PostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.webapp.dto.ClassroomDto;
import ar.edu.itba.paw.webapp.dto.PaginatedFileDto;
import ar.edu.itba.paw.webapp.dto.PostDto;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.OperationFailedException;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Path("/classroom")
@Component
public class ClassroomController {
    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private PostService postService;

    @Context
    private UriInfo uriInfo;

//    @GET
//    @Path("/")
//    public Response getClassroom() {
//        lectureService.findClassesByStudentAndStatus()
//        return Response.ok().build();
//    }

    @GET
    @Path("/{classId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getClassroom(@PathParam("classId") final Long classId) {
        Lecture lecture = checkLectureExistence(classId);
        return Response.ok(
                ClassroomDto.getClassroom(uriInfo ,lecture)
        ).build();
    }

    @GET
    @Path("/{classId}/posts")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getClassroomComments(@PathParam("classId") final Long classId,
                                         @QueryParam("page") @DefaultValue("1") Integer page,
                                         @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        Lecture lecture = checkLectureExistence(classId);
        lecture.getClassPosts().sort(Comparator.comparing(Post::getTime));
        final List<PostDto> ans = lecture.getClassPosts().stream().map(post -> PostDto.getPostDto(uriInfo, post)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<PostDto>>(ans){}).build();
    }

    @GET
    @Path("/{classId}/files")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getClassroomFiles(@PathParam("classId") final Long classId) {
        Lecture lecture = checkLectureExistence(classId);
        final List<PaginatedFileDto> ans = lecture.getSharedFilesByTeacher().stream().map(e -> PaginatedFileDto.getPaginatedFileDto(uriInfo, "files", "",e.getFileName(), e.getFileId())).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<PaginatedFileDto>>(ans){}).build();
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
        return Response.created(location).build();
    }

    private Lecture checkLectureExistence(Long classId) throws ClassNotFoundException {
        Optional<Lecture> maybeClass = lectureService.findById(classId);
        if (!maybeClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + classId);
        }
        return maybeClass.get();
    }
}
