package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserAlreadyExistException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.requestDto.*;
import ar.edu.itba.paw.webapp.security.api.models.Authority;
import ar.edu.itba.paw.webapp.security.services.AuthenticationTokenService;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/api/users")
@Component
public class UsersController {

    private static final int ALREADY_INSERTED = 0, NO_CONTENT_TO_DELETE = 0;

    @Autowired
    AuthenticationTokenService authenticationTokenService;

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRoleService userRoleService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    // UserService
    @POST
    @Path("/teacher")
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response registerTeacher(@Validated(NewUserDto.Teacher.class) @RequestBody NewUserDto newUserDto) {
        LOGGER.debug("Registering teacher of name {}", newUserDto.getName());
        return commonRegister(newUserDto);
    }

    // UserService
    @POST
    @Path("/student")
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response registerStudent(@Validated(NewUserDto.Student.class) @RequestBody NewUserDto newUserDto) {
        LOGGER.debug("Registering student of name {}", newUserDto.getName());
        return commonRegister(newUserDto);
    }

    private Response commonRegister(NewUserDto newUserDto) {
        userService.findByEmail(newUserDto.getMail()).orElseThrow(UserAlreadyExistException::new);
        Optional<User> newUser = userService.create(newUserDto.getName(), newUserDto.getMail(), newUserDto.getPassword(),
                newUserDto.getDescription(), newUserDto.getSchedule(), newUserDto.getRole());
        if(!newUser.isPresent())
            return Response.status(Response.Status.CONFLICT).build();
        LOGGER.debug("User {} registered, Role: {}", newUser.get().getName(), newUserDto.getRole());
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + newUser.get().getId());
        Response.ResponseBuilder response = Response.created(location);
        response.entity(AuthDto.fromUser(newUser.get()));
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(Authority.USER_STUDENT);
        if (newUser.get().isTeacher()) authoritySet.add(Authority.USER_TEACHER);
        return response.header("Authorization", authenticationTokenService.issueToken(newUser.get().getMail(), authoritySet)).build();
    }

    // UserService
    @GET
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response findBySubject(@QueryParam("search") String search,
                                  @QueryParam("maxPrice") @DefaultValue("10000") Integer price, //TODO define better defaults not my
                                  @QueryParam("level") @DefaultValue("0") Integer level,
                                  @QueryParam("rating") @DefaultValue("0") Integer rating,
                                  @QueryParam("order") @DefaultValue("0") Integer order,
                                  @QueryParam("page") @DefaultValue("1") Integer page,
                                  @QueryParam("pageSize") @DefaultValue("10") Integer pageSize ) {
        try {
            final Page<TeacherInfo> filteredTeaches = teachesService.filterUsers(search, order, price, level, rating, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<TeacherDto>>(filteredTeaches.getContent().stream()
                            .map(TeacherDto::getTeacher)
                            .collect(Collectors.toList())) {
                    });
            return PaginationBuilder.build(filteredTeaches, builder, uriInfo, pageSize);
        } catch (IllegalArgumentException exception) { //TODO mensaje exacto
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
   }
//    @GET
//    @Path("/filters")
//    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
//    public Response filterTeachers(@QueryParam("page") int page, @QueryParam("search") String search, @QueryParam("price") Integer price,
//                                   @QueryParam("level") Integer level, @QueryParam("rating") Integer rating, @QueryParam("order") Integer order) {
//        final List<TeacherDto> filteredTeachers = teachesService.filterUsers(search, order, price, level, rating, page).stream()
//                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
//        int total = teachesService.getPageQty(search, price, level, rating);
//        return addPaginationHeaders(page, total, Response.ok(new GenericEntity<List<TeacherDto>>(filteredTeachers){}));
//    }

    // UserService
    @GET
    @Path("/{id}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getTeacherInfo(@PathParam("id") Long id) {
        final User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        TeacherInfo teacherInfo = teachesService.getTeacherInfo(user.getId())
                    .orElse(new TeacherInfo(user.getId(), user.getName(), 0, 0, user.getDescription(), 0.0f, user.getSchedule(),
                            user.getMail(), 0));
        return Response.ok(TeacherDto.getTeacher(teacherInfo)).build();
    }

    // UserService
    @GET
    @Path("/top-rated")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response listTopRatedTeachers() {
        final List<TeacherDto> topRatedTeachers = teachesService.getTopRatedTeachers().stream()
                .map(TeacherDto::getTeacher).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(topRatedTeachers){}).build();
    }

    // UserService
    @GET
    @Path("/most-requested")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response listMostRequestedTeachers() {
        final List<TeacherDto> mostRequestedTeachers = teachesService.getMostRequested().stream()
                .map(TeacherDto::getTeacher).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(mostRequestedTeachers){}).build();
    }

    // UserService
    // Edit profile
    @POST
    @Path("/{uid}/teacher")
    @Consumes("application/vnd.getaproff.api.v1+json")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response editProfileTeacher(@PathParam("uid") Long uid, @Validated(EditUserDto.Teacher.class) @RequestBody EditUserDto editUserDto) {
        int desc = userService.setUserDescription(uid, editUserDto.getDescription());
        int sch = userService.setUserSchedule(uid, editUserDto.getSchedule());
        int name = userService.setUserName(uid, editUserDto.getName());
        if (editUserDto.getSwitchRole().equals("false")) {
            return (desc == 1 && sch == 1 && name == 1) ? Response.status(Response.Status.ACCEPTED).build() : Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<User> user = userService.findById(uid);
        boolean added = userRoleService.addRoleToUser(uid, Roles.TEACHER.getId());
        userService.setTeacherAuthorityToUser();
        return (desc == 1 && sch == 1 && name == 1 && added && user.isPresent()) ?
                Response.ok(AuthDto.fromUser(user.get())).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    // UserService
    @POST
    @Path("/{uid}/student")
    @Consumes("application/vnd.getaproff.api.v1+json")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response editProfileStudent(@PathParam("uid") Long uid, @Validated(EditUserDto.Student.class) @RequestBody EditUserDto editUserDto) {
        int name = userService.setUserName(uid, editUserDto.getName());
        return name == 1 ? Response.status(Response.Status.ACCEPTED).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Subjects
    // UserService
    @GET
    @Path("/{id}/subjects")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getSubjectInfoFromUser(@PathParam("id") Long id) {
        final List<SubjectInfoDto> subjectInfoDtos = teachesService.get(id).stream()
                .collect(Collectors.groupingBy(teaches -> teaches.getSubject().getName())).entrySet().stream()
                .map(k -> SubjectInfoDto.fromSubjectInfo(k.getKey(), k.getValue())).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectInfoDto>>(subjectInfoDtos){}).build();
    }

//    //TODO: SE PODRIA BORRAR
//    @GET
//    @Path("/subjects/levels/{id}")
//    @Produces("application/vnd.getaproff.api.v1+json")
//    public Response getSubjectsAndLevelsTaughtByUser(@PathParam("id") Long id) {
//        final List<SubjectLevelDto> subjectLevelDtos = teachesService.getSubjectAndLevelsTaughtByUser(id)
//                .entrySet().stream().map(entry -> SubjectLevelDto.fromSubjectLevel(uriInfo, entry)).collect(Collectors.toList());
//        return Response.ok(new GenericEntity<List<SubjectLevelDto>>(subjectLevelDtos){}).build();
//    }

    // UserService
    @GET
    @Path("/available-subjects/{id}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getSubjectAndLevelsAvailableForUser(@PathParam("id") Long id) {
        final List<SubjectLevelDto> subjectLevelDtos = teachesService.getSubjectAndLevelsAvailableForUser(id)
                .entrySet().stream().map(SubjectLevelDto::fromSubjectLevel).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectLevelDto>>(subjectLevelDtos){}).build();
    }

    // UserService
    @POST
    @Path("/{uid}")
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response addSubjectToUser(@PathParam("uid") Long userId, @Valid @RequestBody SubjectRequestDto newSubjectDto) {
        final Optional<Teaches> newTeaches = teachesService.addSubjectToUser(userId, newSubjectDto.getSubjectId(),
                newSubjectDto.getPrice(), newSubjectDto.getLevel());
        if (newTeaches.isPresent()) {
            LOGGER.debug("Subject with id {} added to user with id: {}", newSubjectDto.getSubjectId() ,userId);
        }
        return newTeaches.isPresent() ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    // UserService
    @DELETE
    @Path("/{userId}/{subjectId}/{level}")
    public Response removeSubjectsTaughtFromUser(@PathParam("userId") Long userId, @PathParam("subjectId") Long subjectId, @PathParam("level") int level) {
        return teachesService.removeSubjectToUser(userId, subjectId, level) == 1 ?
                Response.status(Response.Status.OK).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    //Returns all the classes that involve the user
//    @GET
//    @Path("/{uid}/classes")
//    public Response getClassesFromUser(@PathParam("uid") Long uid){
//        List<Lecture> lectures = lectureService.findClassesByTeacherAndStatus(uid, 3);
//        if (lectures.isEmpty())
//            return Response.status(Response.Status.NO_CONTENT).build();
//        List<ClassroomDto> dtos = lectures.stream().map(lecture -> ClassroomDto.getClassroom(uriInfo,lecture)).collect(Collectors.toList());
//        return Response.ok(new GenericEntity<List<ClassroomDto>>(dtos){}).build();
//    }

    // UserService
    @GET
    @Path("/{uid}/image")
    public Response getUserImage(@PathParam("uid") Long uid) {
        Image image = imageService.findImageById(uid).orElseThrow(ImageNotFoundException::new);
        return Response.ok(ImageDto.fromUser(image)).build();

    }

    // UserService
    @POST
    @Path("/{uid}/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response postImage(@PathParam("uid") Long uid, @FormDataParam("image") InputStream fileStream,
                              @FormDataParam("image") FormDataContentDisposition fileMetadata) throws IOException {
        Optional<Image> image = imageService.createOrUpdate(uid, IOUtils.toByteArray(fileStream)); // TOdo some exception 409
        if (image.isPresent()) {
            LOGGER.debug("Image uploaded for user {}", uid);
        }
        return image.isPresent() ? Response.status(Response.Status.OK).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}

