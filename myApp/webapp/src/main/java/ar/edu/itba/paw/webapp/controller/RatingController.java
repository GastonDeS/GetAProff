package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.RatingService;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.webapp.dto.RatingDto;
import ar.edu.itba.paw.webapp.requestDto.NewRatingDto;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/ratings")
@Component
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { "application/vnd.getaproff.api.v1+json", })
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


}
