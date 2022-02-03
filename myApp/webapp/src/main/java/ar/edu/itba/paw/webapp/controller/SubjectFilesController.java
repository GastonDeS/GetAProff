package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SubjectFileService;
import ar.edu.itba.paw.webapp.dto.SubjectFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("api/subject-files")
@Component
public class SubjectFilesController {

    @Autowired
    private SubjectFileService subjectFileService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUserSubjectFiles(@PathParam("id") Long id) {
        final List<SubjectFileDto> subjectFileDtos = subjectFileService.getAllSubjectFilesFromUser(id).stream()
                .map(SubjectFileDto::fromUser).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectFileDto>>(subjectFileDtos){}).build();
    }

//    @POST
//    @Path("/{id}")
//    public Response uploadUserSubjectFiles(@PathParam("id") Long id, @RequestParam("files") MultipartFile[] files) {
//
//    }

}
