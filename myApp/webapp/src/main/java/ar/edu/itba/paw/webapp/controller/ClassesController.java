package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.MailNotSentException;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.forms.AcceptForm;
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
import java.util.stream.Collectors;

@Controller
public class ClassesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private PostService postService;

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
        List<Class> classesList;
        if (type.equals("requested")) {
            classesList = classService.findClassesByStudentAndStatus(userId, status);
            mav.addObject("allClasses", classesList);
        }
        else if (type.equals("offered")) {
            classesList = classService.findClassesByTeacherAndStatus(userId, status);
            mav.addObject("allClasses", classesList);
        }
        else {
            throw new InvalidParameterException("exception.invalid.parameter");
        }
        return mav;
    }

    @RequestMapping(value = "/myClasses/{cid}/{status}", method = RequestMethod.POST)
    public ModelAndView classesStatusChange(@PathVariable("cid") final Long cid, @PathVariable final String status) {
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        if (status.equals("STUDENT") || status.equals("TEACHER")) {
            if (myClass.get().getDeleted() == 0) {
                classService.setDeleted(cid, Class.Deleted.valueOf(status).getValue());
            } else {
                classService.setDeleted(cid, Class.Deleted.BOTH.getValue());
            }
        } else {
            classService.setStatus(cid, Class.Status.valueOf(status).getValue());
            myClass.get().setStatus(Class.Status.valueOf(status).getValue());
            try {
                emailService.sendStatusChangeMessage(myClass.get());
            } catch (MailNotSentException exception) {
                throw new OperationFailedException("exception.failed");
            }
        }
        LOGGER.debug("Class " + cid + "changed to status " + status);
        return new ModelAndView("redirect:/myClasses");
    }


    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.GET)
    public ModelAndView acceptForm(@ModelAttribute("acceptForm") final AcceptForm form, @PathVariable("cid") final Long cid) {
        final ModelAndView mav = new ModelAndView("acceptForm");
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        Optional<User> student = userService.findById(myClass.get().getStudent().getId());
        if (!student.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        return mav.addObject("student", student.get().getName()).addObject("uid", myClass.get().getTeacher().getId());
    }

    @RequestMapping(value = "/accept/{cid}", method = RequestMethod.POST)
    public ModelAndView accept(@PathVariable("cid") final Long cid, @ModelAttribute("acceptForm") @Valid final AcceptForm form,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return acceptForm(form, cid);
        }
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        classService.setStatus(myClass.get().getClassId(), Class.Status.ACCEPTED.getValue());
        classService.setReply(myClass.get().getClassId(), form.getMessage());
        try {
            emailService.sendAcceptMessage(myClass.get().getStudent().getId(), myClass.get().getTeacher().getId(), (long) 3, form.getMessage());
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Class accepted by teacher " + myClass.get().getTeacher().getId() + " for stutent " + myClass.get().getStudent().getId());
        return new ModelAndView("redirect:/myClasses");
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.GET)
    public ModelAndView rateForm(@ModelAttribute("rateForm") final RateForm form, @PathVariable("cid") final Long cid) {
        final ModelAndView mav = new ModelAndView("rateForm");
        Optional<Class> myClass = classService.findById(cid);
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
        Optional<Class> myClass = classService.findById(cid);
        if (!myClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + cid);
        }
        classService.setStatus(cid, Class.Status.RATED.getValue());
        myClass.get().setStatus(Class.Status.RATED.getValue());
        //TODO: chequear si se agrego
        ratingService.addRating(myClass.get().getTeacher(), myClass.get().getStudent(), form.getRating().floatValue(), form.getReview());
        try {
            emailService.sendRatedMessage(myClass.get(), form.getRating(), form.getReview());
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Class rated by student " + myClass.get().getStudent().getId() + " for teacher " + myClass.get().getTeacher().getId());
        return new ModelAndView("redirect:/myClasses");
    }

    @RequestMapping(value = "/classroom/{classId}", method = RequestMethod.GET)
    public ModelAndView accessClassroom(@PathVariable("classId") final Long classId, @ModelAttribute("classUploadForm") @Valid final ClassUploadForm form) {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent())
            throw new NoUserLoggedException("exception.not.logger.user");
        Optional<Class> maybeClass = classService.findById(classId);
        if (!maybeClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + classId);
        }
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
        return accessClassroom(classId, new ClassUploadForm());
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

}
