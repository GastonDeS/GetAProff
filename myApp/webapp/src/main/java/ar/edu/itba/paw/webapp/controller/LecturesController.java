package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.exceptions.MailNotSentException;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.forms.ClassUploadForm;
import ar.edu.itba.paw.webapp.forms.RateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class LecturesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturesController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private PostService postService;

    @Autowired
    private TeachesService teachesService;

    @RequestMapping("/myClasses?error=true")
    public ModelAndView myClassesError() {
        return myClasses("requested", 3);
    }

    @RequestMapping(value = "/myClasses/{type}/{status}", method = RequestMethod.GET)
    public ModelAndView myClasses(@PathVariable("type") String type, @PathVariable("status") Integer status) {
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            throw new NoUserLoggedException("exception.not.logger.user");
        }
        if (status < 0 || status > 3) {
            throw new InvalidParameterException("exception.invalid.parameter");
        }
        Long userId = user.get().getId();
        LOGGER.debug("Accessing classes of user with id: " + userId);
        final ModelAndView mav = new ModelAndView("classes")
                .addObject("user", user.get());
        List<Lecture> lecturesList;
        if (type.equals("requested")) {
            lecturesList = lectureService.findClassesByStudentAndStatus(userId, status);
            mav.addObject("allLectures", lecturesList);
        } else if (type.equals("offered")) {
            lecturesList = lectureService.findClassesByTeacherAndStatus(userId, status);
            mav.addObject("allLectures", lecturesList);
        } else {
            throw new InvalidParameterException("exception.invalid.parameter");
        }
        return mav.addObject("type", type).addObject("status", status);
    }

    @RequestMapping(value = "/myClasses/{from}/{cid}/{status}", method = RequestMethod.POST)
    public ModelAndView classesStatusChange(@PathVariable("from") final int from,@PathVariable("cid") final Long cid, @PathVariable final String status) {
        Optional<Lecture> myClass = lectureService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        int intStatus = Lecture.Status.valueOf(status).getValue();
        String offered = "offered";
        lectureService.setStatus(cid, intStatus);
        myClass.get().setStatus(intStatus);
        try {
            emailService.sendStatusChangeMessage(myClass.get());
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Lecture " + cid + "changed to status " + status);
        if (from != 0){
            return new ModelAndView("redirect:/classroom/" + cid);
        }
        if (intStatus > 2){
            if (intStatus == 3) {
                offered = "requested";
            }
            intStatus = 2;
        }
        return new ModelAndView("redirect:/myClasses/" + offered +"/" + intStatus);
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.GET)
    public ModelAndView rateForm(@ModelAttribute("rateForm") final RateForm form, @PathVariable("cid") final Long cid) {
        final ModelAndView mav = new ModelAndView("rateForm");
        Optional<Lecture> myClass = lectureService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        Optional<User> teacher = userService.findById(myClass.get().getTeacher().getId());
        if (!teacher.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        return mav.addObject("teacher", teacher.get().getName());
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.POST)
    public ModelAndView rate(@PathVariable("cid") final Long cid, @ModelAttribute("rateForm") @Valid final RateForm form,
                             final BindingResult errors) {
        if (errors.hasErrors()) {
            return rateForm(form, cid);
        }
        Optional<Lecture> myClass = lectureService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        lectureService.setStatus(cid, Lecture.Status.RATED.getValue());
        myClass.get().setStatus(Lecture.Status.RATED.getValue());
        //TODO: chequear si se agrego
        ratingService.addRating(myClass.get().getTeacher(), myClass.get().getStudent(), form.getRating().floatValue(), form.getReview());
        try {
            emailService.sendRatedMessage(myClass.get(), form.getRating(), form.getReview());
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Lecture rated by student " + myClass.get().getStudent().getId() + " for teacher " + myClass.get().getTeacher().getId());
        return new ModelAndView("redirect:/myClasses/requested/2");
    }

    @RequestMapping(value = "/classroom/{classId}", method = RequestMethod.GET)
    public ModelAndView accessClassroom(@PathVariable("classId") final Long classId, @ModelAttribute("classUploadForm") @Valid final ClassUploadForm form) {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent())
            throw new NoUserLoggedException("exception.not.logger.user");
        Optional<Lecture> maybeClass = lectureService.findById(classId);
        if (!maybeClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + classId);
        }
        System.out.println(form.getMessage() + "HOLA");
        return new ModelAndView("classroom")
                .addObject("currentUser", maybeUser.get())
                .addObject("currentClass", maybeClass.get())
                .addObject("posts", postService.retrievePosts(classId));
    }

    @RequestMapping(value = "/classroom/{classId}", method = RequestMethod.POST)
    public ModelAndView publishPost(@PathVariable("classId") final Long classId, @ModelAttribute("classUploadForm") @Valid final ClassUploadForm form,
                                    final BindingResult errors) throws IOException {
        if (errors.hasErrors()) return accessClassroom(classId, form);
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent())
            throw new NoUserLoggedException("exception.not.logger.user");
        postService.post(maybeUser.get().getId(), classId, form.getFile().getOriginalFilename(), form.getFile().getBytes(), form.getMessage(), form.getFile().getContentType());
        return new ModelAndView("redirect:/classroom/" + classId);
    }

    @RequestMapping(value = "/classroom/open/{postId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> openFile(@PathVariable("postId") final Long postId) {
        Post post = postService.getFileData(postId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(post.getType()));
        headers.add("Content-Disposition", "inline; filename=" + post.getFilename());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(post.getFile(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/requestClass/{uid}/{subjectId}/{level}", method = RequestMethod.POST)
    public ModelAndView classesStatusChange(@PathVariable("uid") final Long uid, @PathVariable("subjectId") final Long subjectId, @PathVariable("level") final int level) {

        Optional<User> user = userService.findById(uid);
        Optional<User> curr = userService.getCurrentUser();
        Optional<Teaches> t = teachesService.findByUserAndSubjectAndLevel(uid, subjectId, level);

        if (!t.isPresent() || !user.isPresent() || !curr.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        Lecture newLecture = lectureService.create(curr.get().getId(), uid, t.get().getLevel(), t.get().getSubject().getId(), t.get().getPrice());
        try {
            emailService.sendNewClassMessage(user.get().getMail(), curr.get().getName(), t.get().getSubject().getName());
        } catch (RuntimeException exception) {
            throw new OperationFailedException("exception");
        }
        LOGGER.debug("User {} requested class from teacher {}", curr.get().getId(), uid);
        return new ModelAndView("redirect:/classroom/" + newLecture.getClassId().toString());
    }
}
