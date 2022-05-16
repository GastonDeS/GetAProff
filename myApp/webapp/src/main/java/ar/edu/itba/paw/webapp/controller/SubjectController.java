package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/subjects")
@Component
public class SubjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getAllSubjects() {
        final List<SubjectDto> subjectDtos = subjectService.list().stream()
                .map(subject -> SubjectDto.get(uriInfo, subject)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectDto>>(subjectDtos){}).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getSubject(@PathParam("id") Long id) {
        final Optional<Subject> subject = subjectService.findById(id);
        return subject.isPresent() ? Response.ok(SubjectDto.get(uriInfo, subject.get())).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/most-requested")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getMostRequestedSubjects() {
        final List<SubjectDto> subjectDtos = subjectService.getHottestSubjects().stream()
                .map(subject -> SubjectDto.get(uriInfo, subject)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectDto>>(subjectDtos){}).build();
    }

    

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TeachesService teachesService;
//
//    @Autowired
//    private EmailService emailService;
//
//    private User getCurrUser() {
//        Optional<User> maybeUser = userService.getCurrentUser();
//        if (!maybeUser.isPresent()) {
//            throw new NoUserLoggedException("exception.not.logger.user");
//        }
//        return maybeUser.get();
//    }
//
//    @RequestMapping(value = "/newSubjectForm", method = RequestMethod.GET)
//    public ModelAndView newSubjectForm(@ModelAttribute("newSubjectForm") final NewSubjectForm form) {
//        Optional<User> maybeUser = userService.getCurrentUser();
//        if (!maybeUser.isPresent()) {
//            throw new NoUserLoggedException("exception.not.logger.user");
//        }
//        return new ModelAndView("newSubjectForm").addObject("userid", maybeUser.get().getId());
//    }
//
//    @RequestMapping(value = "/newSubjectForm", method = RequestMethod.POST)
//    public ModelAndView newSubject(@ModelAttribute("newSubjectForm") @Valid final NewSubjectForm form,
//                               final BindingResult errors) {
//        if (errors.hasErrors()) {
//            return newSubjectForm(form);
//        }
//        Optional<User> maybeUser = userService.getCurrentUser();
//        if (!maybeUser.isPresent()) {
//            throw new NoUserLoggedException("exception.not.logger.user");
//        }
//        try {
//            emailService.sendSubjectRequest(maybeUser.get().getId(), form.getSubject(), form.getMessage());
//        } catch (RuntimeException exception) {
//            throw new OperationFailedException("exception.failed");
//        }
//        LOGGER.debug("Subject request sent for {}", maybeUser.get().getId());
//        return new ModelAndView("redirect:/newSubjectFormSent");
//    }
//
//    @RequestMapping("/newSubjectFormSent")
//    public ModelAndView classRequestSent() {
//        Optional<User> maybeUser = userService.getCurrentUser();
//        if (!maybeUser.isPresent()) {
//            throw new NoUserLoggedException("exception.not.logger.user");
//        }
//        return new ModelAndView("subjectRequestSent").addObject("uid", maybeUser.get().getId());
//    }
//
//    @RequestMapping(value = "/editSubjects", method = RequestMethod.GET)
//    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") final SubjectsForm form) {
//        User currentUser = getCurrUser();
//        List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(currentUser.getId());
//        return new ModelAndView("subjectsForm")
//                .addObject("userid", currentUser.getId())
//                .addObject("given", subjectsGiven)
//                .addObject("subjects", subjectService.list());
//    }
//
//    @RequestMapping(value = "/editSubjects", method = RequestMethod.POST)
//    public ModelAndView subjectsForm(@ModelAttribute("subjectsForm") @Valid final SubjectsForm form, final BindingResult errors) {
//        Long userId = getCurrUser().getId();
//        if (teachesService.findByUserAndSubjectAndLevel(userId, form.getSubjectId(), form.getLevel()).isPresent()) {
//            errors.rejectValue("level","form.level.invalid");
//        }
//        if (errors.hasErrors()) {
//            return subjectsForm(form);
//        }
//        Optional<Teaches> maybe = teachesService.addSubjectToUser(userId, form.getSubjectId(), form.getPrice(), form.getLevel());
//        if (!maybe.isPresent()) {
//            throw new OperationFailedException("exception.failed");
//        }
//        LOGGER.debug("Subject added for user {}", userId);
//        return subjectsForm(form);
//    }
//
//    @RequestMapping(value = "/editSubjects/remove/{sid}/{level}", method = RequestMethod.POST)
//    public ModelAndView removeSubject(@PathVariable("sid") final Long sid, @PathVariable("level") final int level) {
//        Long uid = getCurrUser().getId();
//        if (teachesService.removeSubjectToUser(uid, sid, level) == 0 ) {
//            throw new OperationFailedException("exception.failed");
//        }
//        LOGGER.debug("Subject removed for user {}",uid);
//        return new ModelAndView("redirect:/editSubjects");
//    }
}
