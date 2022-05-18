package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.RatingService;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.webapp.dto.RatingDto;
import ar.edu.itba.paw.webapp.requestDto.NewRatingDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/ratings")
@Component
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private EmailService emailService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private AuthFacade authFacade;

    @GET
    @Path("/{id}")
    @Consumes("application/vnd.getaproff.api.v1+json")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getTeacherRatings(@PathParam("id") Long id,
                                      @QueryParam("page") @DefaultValue("1") Integer page,
                                      @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        try {
            final Page<Rating> ratingDtos = ratingService.getTeacherRatings(id, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<RatingDto>>(ratingDtos.getContent().stream().map(RatingDto::fromRating).collect(Collectors.toList())) {
                    });
            return PaginationBuilder.build(ratingDtos, builder, uriInfo, pageSize);
        } catch (IllegalArgumentException exception) { //TODO mensaje exacto
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // TODO exception
    @POST
    @Path("/{teacherId}")
    @Produces(value = { "application/vnd.getaproff.api.v1+json" })
    @Consumes(value = { "application/vnd.getaproff.api.v1+json" })
    public Response rateTeacher(@PathParam("teacherId") Long teacherId, @Valid @RequestBody NewRatingDto newRatingDto){
        final Optional<Rating> rating = ratingService.addRating(teacherId, authFacade.getCurrentUserId(),
                newRatingDto.getRate(), newRatingDto.getReview());
        if (!rating.isPresent())
            return Response.status(Response.Status.CONFLICT).build();
        //TODO: cual seria el id de la review? teacherid y userid
        URI location = URI.create(uriInfo.getBaseUri() + "/reviews/");
        emailService.sendRatedMessage(teacherId, authFacade.getCurrentUserId(), rating.get(), uriInfo.getBaseUri().toString());
        return Response.created(location).build();
    }
}
