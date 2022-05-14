package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.requestDto.ClassRequestDto;
import ar.edu.itba.paw.webapp.requestDto.NewRatingDto;
import ar.edu.itba.paw.webapp.requestDto.SubjectRequestDto;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

@Path("users")
@Component
public class UsersController {

    private static final int ALREADY_INSERTED = 0, NO_CONTENT_TO_DELETE = 0
            ;
    @Autowired
    private TeachesService teachesService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LectureService lectureService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response findBySubject(@QueryParam("search") String search,
                                  @QueryParam("price") @DefaultValue("10000") Integer price, //TODO define better defaults not my
                                  @QueryParam("level") @DefaultValue("0") Integer level,
                                  @QueryParam("rating") @DefaultValue("0") Integer rating,
                                  @QueryParam("order") @DefaultValue("0") Integer order,
                                  @QueryParam("page") @DefaultValue("1") Integer page,
                                  @QueryParam("pageSize") @DefaultValue("10") Integer pageSize ) {
        try {
            final Page<TeacherInfo> filteredTeaches = teachesService.filterUsers(search, order, price, level, rating, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<TeacherDto>>(filteredTeaches.getContent().stream()
                            .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo))
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

    @POST
    @Path("/{uid}/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response postImage(@PathParam("uid") Long uid, @FormDataParam("image") InputStream fileStream,
                              @FormDataParam("image") FormDataContentDisposition fileMetadata) throws IOException {
        Optional<Image> image = imageService.createOrUpdate(uid, IOUtils.toByteArray(fileStream));
        return image.isPresent() ? Response.status(Response.Status.OK).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response getTeacherInfo(@PathParam("id") Long id) {
        final Optional<User> mayBeUser = userService.findById(id);
        if(!mayBeUser.isPresent()) return Response.status(Response.Status.NOT_FOUND).build();
        User user = mayBeUser.get();
        TeacherInfo teacherInfo = teachesService.getTeacherInfo(user.getId())
                    .orElse(new TeacherInfo(user.getId(), user.getName(), 0, 0, user.getDescription(), 0.0f, user.getSchedule(),
                            user.getMail(), 0));
        return Response.ok(TeacherDto.getTeacher(uriInfo, teacherInfo)).build();
    }

    @GET
    @Path("/top-rated")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response listTopRatedTeachers() {
        final List<TeacherDto> topRatedTeachers = teachesService.getTopRatedTeachers().stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(topRatedTeachers){}).build();
    }

    @GET
    @Path("/most-requested")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response listMostRequestedTeachers() {
        final List<TeacherDto> mostRequestedTeachers = teachesService.getMostRequested().stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(mostRequestedTeachers){}).build();
    }

    @GET
    @Path("/{id}/subjects")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response getSubjectInfoFromUser(@PathParam("id") Long id) {
        final List<SubjectInfoDto> subjectInfoDtos = teachesService.get(id).stream()
                .collect(Collectors.groupingBy(teaches -> teaches.getSubject().getName())).entrySet().stream()
                .map(k -> SubjectInfoDto.fromSubjectInfo(k.getKey(), k.getValue())).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectInfoDto>>(subjectInfoDtos){}).build();
    }

    @GET
    @Path("/subjects/levels/{id}")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response getSubjectsAndLevelsTaughtByUser(@PathParam("id") Long id) {
        final List<SubjectLevelDto> subjectLevelDtos = teachesService.getSubjectAndLevelsTaughtByUser(id)
                .entrySet().stream().map(entry -> SubjectLevelDto.fromSubjectLevel(uriInfo, entry)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectLevelDto>>(subjectLevelDtos){}).build();
    }

    @DELETE
    @Path("/{userId}/{subjectId}/{level}")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response removeSubjectsTaughtFromUser(@PathParam("userId") Long userId, @PathParam("subjectId") Long subjectId, @PathParam("level") int level) {
        return teachesService.removeSubjectToUser(userId, subjectId, level) == 1 ?
                Response.status(Response.Status.OK).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    //Edit profile
    @POST
    @Path("/{id}")
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    @Produces(value = { "application/vnd.getaproff.api.v1+json" })
    public Response editProfile(@PathParam("id") Long id,
                                @FormDataParam("name") String newName,
                                @FormDataParam("description") String newDescription,
                                @FormDataParam("schedule") String newSchedule,
                                @FormDataParam("teach") String toTeacher) {
        int desc = userService.setUserDescription(id, newDescription);
        int sch = userService.setUserSchedule(id, newSchedule);
        int name = userService.setUserName(id, newName);
        if (!toTeacher.equals("true")) {
            return (desc == 1 && sch == 1 && name == 1) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<User> user = userService.findById(id);
        boolean added = userRoleService.addRoleToUser(id, Roles.TEACHER.getId());
        userService.setTeacherAuthorityToUser();
        return (desc == 1 && sch == 1 && name == 1 && added && user.isPresent()) ?
                Response.ok(AuthDto.fromUser(uriInfo, user.get())).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }


    @GET
    @Path("/available-subjects/{id}")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response getSubjectAndLevelsAvailableForUser(@PathParam("id") Long id) {
        final List<SubjectLevelDto> subjectLevelDtos = teachesService.getSubjectAndLevelsAvailableForUser(id)
                .entrySet().stream().map(entry -> SubjectLevelDto.fromSubjectLevel(uriInfo, entry)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectLevelDto>>(subjectLevelDtos){}).build();
    }

    @POST
    @Path("/{uid}")
    @Consumes(value = { "application/vnd.getaproff.api.v1+json", })
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
    public Response addSubjectToUser(@PathParam("uid") Long userId, @Valid @RequestBody NewSubjectDto newSubjectDto) {
        final Optional<Teaches> newTeaches = teachesService.addSubjectToUser(userId, newSubjectDto.getSubjectId(),
                newSubjectDto.getPrice(), newSubjectDto.getLevel());
        return newTeaches.isPresent() ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
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

    //Return all favorite users of user with uid
    @GET
    @Path("/{uid}/favorites")
    public Response getUserFavorites(@PathParam("uid") Long uid,
                                    @QueryParam("page") @DefaultValue("1") Integer page,
                                    @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        try {
            final Page<TeacherInfo> favourites = userService.getFavourites(uid, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<TeacherDto>>(favourites.getContent().stream()
                            .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo))
                            .collect(Collectors.toList())) {
                    });
            return PaginationBuilder.build(favourites, builder, uriInfo, pageSize);
        } catch (IllegalArgumentException exception) { // TODO mensaje exacto
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

//TODO: esto colisiona
//    @GET
//    @Path("/{uid}")
//    @Produces({"application/vnd.getaproff.api.v1+json"})
//    public Response isFaved(@PathParam("uid") Long teacherId, @QueryParam("favedBy") Long uid) {
//        boolean isFaved = userService.isFaved(teacherId, uid);
//        return Response.ok(IsFavedDto.createIsFavedDto(isFaved)).build();
//    }

    //Add/remove new user to user with uid favorites list
    @POST
    @Path("/{uid}/favorites")
    @Consumes(value = "application/vnd.getaproff.api.v1+json")
    public Response addNewFavoriteUser(@PathParam("uid") Long uid, IdDto teacherId) {
        int result = userService.addFavourite(teacherId.getId(), uid);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{uid}/favorites/{favTeacherId}")
    public Response removeFavoriteUser(@PathParam("uid") Long uid, @PathParam("favTeacherId") Long teacherId) {
        int result = userService.removeFavourite(teacherId, uid);
        if (result == NO_CONTENT_TO_DELETE)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok().build();
    }

    @GET
    @Path("/{uid}/image")
    public Response getUserImage(@PathParam("uid") Long uid) {
        Optional<Image> maybeImage = imageService.findImageById(uid);
        return maybeImage.isPresent() ? Response.ok(ImageDto.fromUser(uriInfo, maybeImage.get())).build()
                : Response.status(Response.Status.NO_CONTENT).build();

    }

    @POST
    @Path("/{uid}/classes")
    @Consumes(value = "application/vnd.getaproff.api.v1+json")
    public Response requestClass(@PathParam("uid") Long teacherId, ClassRequestDto classRequestDto){
        Optional<Lecture> newLecture = lectureService.create(classRequestDto.getStudentId(), teacherId, classRequestDto.getLevel(),
                classRequestDto.getSubjectId(), classRequestDto.getPrice());
        if(!newLecture.isPresent()){
          return Response.status(Response.Status.CONFLICT).build();
        }
        URI location = URI.create(uriInfo.getBaseUri() + "classroom/" + newLecture.get().getClassId());
        return Response.created(location).build();
    }

    @POST
    @Path("/{uid}/reviews")
    @Produces(value = { "application/vnd.getaproff.api.v1+json" })
    @Consumes(value = { "application/vnd.getaproff.api.v1+json" })
    public Response rateTeacher(@PathParam("uid") Long teacherId, NewRatingDto newRatingDto){
        //TODO: validar que exista la clase entre alumno y teacher y que este en estado terminado
        final Optional<Rating> rating = ratingService.addRating(newRatingDto.getTeacherId(), newRatingDto.getStudentId(),
                    newRatingDto.getRate(), newRatingDto.getReview());
        if (!rating.isPresent())
            return Response.status(Response.Status.CONFLICT).build();
        if(newRatingDto.getTeacherId().equals(newRatingDto.getStudentId()))
            return Response.status(Response.Status.FORBIDDEN).build();
        //TODO: cual seria el id de la review?
        URI location = URI.create(uriInfo.getBaseUri() + "/reviews/");
        return Response.created(location).build();
    }
}

