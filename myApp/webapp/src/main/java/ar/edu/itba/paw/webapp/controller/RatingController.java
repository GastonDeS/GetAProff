package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.RatingService;
import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.webapp.dto.RatingDto;
import ar.edu.itba.paw.webapp.requestDto.NewRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("ratings")
@Component
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getTeacherRatings(@PathParam("id") Long id) {
        final List<RatingDto> ratingDtos = ratingService.getTeacherRatings(id).stream()
                .map(RatingDto::fromRating).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<RatingDto>>(ratingDtos){}).build();
    }

    @POST
    @Path("/new-rating")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    @Consumes(value = { MediaType.APPLICATION_JSON, })
    public Response addRating(NewRatingDto newRatingDto) {
        final Optional<Rating> rating = ratingService.addRating(newRatingDto.getTeacherId(), newRatingDto.getStudentId(),
                newRatingDto.getRate(), newRatingDto.getReview());
        return rating.isPresent() ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
