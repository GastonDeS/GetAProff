package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.TeachesService;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("api/subjects")
@Component
public class SubjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private TeachesService teachesService;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getSubjectsTaughtFromUser(@PathParam("id") Long id) {
        final List<SubjectDto> subjectDtos = teachesService.getSubjectInfoListByUser(id).stream()
                .map(SubjectDto::fromSubjectInfo).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectDto>>(subjectDtos){}).build();
    }

    @DELETE
    @Path("/{userId}/{id}/{level}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response removeSubjectsTaughtFromUser(@PathParam("userId") Long userId, @PathParam("id") Long id, @PathParam("level") int level) {
        return teachesService.removeSubjectToUser(userId, id, level) == 1 ?
                Response.status(Response.Status.OK).build() : Response.status(Response.Status.BAD_REQUEST).build();
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
