package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.requestDto.NewSubjectDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.NotFoundStatusMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/subjects")
@Controller
public class SubjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthFacade authFacade;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getAllSubjects() {
        final List<SubjectDto> subjectDtos = subjectService.list().stream()
                .map(s -> SubjectDto.get(s,true)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectDto>>(subjectDtos){}).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getSubject(@PathParam("id") Long id) {
        final Subject subject = subjectService.findById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.SUBJECT));
        return Response.ok(SubjectDto.get(subject, false)).build();
    }

    @GET
    @Path("/most-requested")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getMostRequestedSubjects() {
        final List<SubjectDto> subjectDtos = subjectService.getHottestSubjects().stream()
                .map(e -> SubjectDto.get(e, true)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectDto>>(subjectDtos){}).build();
    }

    @POST
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response requestSubject(@Valid @RequestBody NewSubjectDto newSubjectDto) {
        long uid = authFacade.getCurrentUserId();
        LOGGER.debug("User {} requested subject {}, message: {}", uid, newSubjectDto.getSubject(), newSubjectDto.getMessage());
        emailService.sendSubjectRequest(uid, newSubjectDto.getSubject(), newSubjectDto.getMessage());
        return Response.ok().build();
    }
}
