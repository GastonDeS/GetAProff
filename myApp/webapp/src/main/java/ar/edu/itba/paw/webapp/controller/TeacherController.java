package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

@Path("api")
@Component
public class TeacherController {

    @Autowired
    private TeachesService teachesService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("top-rated")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listTopRatedTeachers() {
        List<CardProfile> topRatedTeachers = teachesService.getTopRatedTeachers();
        return Response.ok(TeacherDto.topRatedTeachers(uriInfo, topRatedTeachers)).build();
    }

//    @RequestMapping("/")
//    public ModelAndView index() {
//        Optional<User> curr = userService.getCurrentUser();
//        List<Subject> subjectList = subjectService.list();
//        final ModelAndView mav = new ModelAndView("index")
//                .addObject("subjects", subjectList)
//                .addObject("topRated", teachesService.getTopRatedTeachers())
//                .addObject("hottest", teachesService.getMostRequested())
//                .addObject("hottestSubjects", subjectService.getHottestSubjects());
//        curr.ifPresent(user -> mav.addObject("uid", user.getId()));
//        return mav;
//    }
}
