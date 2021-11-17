package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.exceptions.MailNotSentException;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.forms.ClassUploadForm;
import ar.edu.itba.paw.webapp.forms.ContactForm;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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
    private SubjectFileService subjectFileService;

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
        User currentUser = checkCurrentUser();
        if (status < 0 || status > 3) {
            throw new InvalidParameterException("exception.invalid.parameter");
        }
        Long userId = currentUser.getId();
        LOGGER.debug("Accessing classes of user with id: " + userId);
        final ModelAndView mav = new ModelAndView("classes")
                .addObject("user", currentUser);
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
    public ModelAndView classesStatusChange(@PathVariable("from") final int from, @PathVariable("cid") final Long cid, @PathVariable final String status, HttpServletRequest request) throws MalformedURLException {
        Lecture currentLecture = checkLectureExistence(cid);
        int intStatus = Lecture.Status.valueOf(status).getValue();
        String offered = "offered";
        lectureService.setStatus(cid, intStatus);
        currentLecture.setStatus(intStatus);
        String localAddress = localAddress(request, "/myClasses");
        try {
            emailService.sendStatusChangeMessage(currentLecture, localAddress);
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Lecture " + cid + "changed to status " + status);
        if (from != 0) {
            return new ModelAndView("redirect:/classroom/" + cid);
        }
        if (intStatus > 2){
            if (intStatus == 3) {
                offered = "requested";
            }
            intStatus = 2;
        }
        return new ModelAndView("redirect:/myClasses/" + offered + "/" + intStatus);
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.GET)
    public ModelAndView rateForm(@ModelAttribute("rateForm") final RateForm form, @PathVariable("cid") final Long cid) {
        final ModelAndView mav = new ModelAndView("rateForm");
        User currentUser = checkCurrentUser();
        Lecture currentLecture = checkLectureExistence(cid);
        Optional<User> teacher = userService.findById(currentLecture.getTeacher().getId());
        if (!teacher.isPresent()) {
            throw new InvalidOperationException("exception.invalid");
        }
        return mav.addObject("teacher", teacher.get().getName()).addObject("userid", currentUser.getId());
    }

    @RequestMapping(value = "/rate/{cid}", method = RequestMethod.POST)
    public ModelAndView rate(@PathVariable("cid") final Long cid, @ModelAttribute("rateForm") @Valid final RateForm form,
                             final BindingResult errors, HttpServletRequest request) throws MalformedURLException {
        if (errors.hasErrors()) {
            return rateForm(form, cid);
        }
        Lecture currentLecture = checkLectureExistence(cid);
        lectureService.setStatus(cid, Lecture.Status.RATED.getValue());
        currentLecture.setStatus(Lecture.Status.RATED.getValue());
        Optional<Rating> newRating = ratingService.addRating(currentLecture.getTeacher(), currentLecture.getStudent(), form.getRating().floatValue(), form.getReview());
        if (!newRating.isPresent()) throw new OperationFailedException("exception.failed");
        String localAddress = localAddress(request, "/rate");
        try {
            emailService.sendRatedMessage(currentLecture, form.getRating(), form.getReview(), localAddress);
        } catch (MailNotSentException exception) {
            throw new OperationFailedException("exception.failed");
        }
        LOGGER.debug("Lecture rated by student " + currentLecture.getStudent().getId() + " for teacher " + currentLecture.getTeacher().getId());
        return new ModelAndView("redirect:/myClasses/requested/2");
    }

    @RequestMapping(value = "/classroom/{classId}", method = RequestMethod.GET)
    public ModelAndView accessClassroom(@PathVariable("classId") final Long classId, @ModelAttribute("classUploadForm") @Valid final ClassUploadForm form) {
        Lecture currentLecture = checkLectureExistence(classId);
        User currentUser = checkCurrentUser();
        checkAccessToClassroom(currentUser, currentLecture);
        if (currentLecture.getStatus() >= 3) throw new ClassNotFoundException("No class found for class id " + classId);
        lectureService.refreshTime(currentLecture.getClassId(), currentUser.getId().equals(currentLecture.getTeacher().getId()) ? 0  : 1);
        return new ModelAndView("classroom")
                .addObject("currentUser", currentUser)
                .addObject("currentClass", currentLecture)
                .addObject("sharedFiles",lectureService.getSharedFilesByTeacher(currentLecture.getClassId()))
                .addObject("teacherFiles", lectureService.getFilesNotSharedInLecture(classId, currentLecture.getTeacher().getId()))
                .addObject("posts", postService.retrievePosts(classId));
    }

    @RequestMapping(value = "/classroom/{classId}", method = RequestMethod.POST)
    public ModelAndView publishPost(@PathVariable("classId") final Long classId, @ModelAttribute("classUploadForm") @Valid final ClassUploadForm form,
                                    final BindingResult errors, HttpServletRequest request) throws IOException {
        if (form.getMessage().isEmpty() && form.getFile().isEmpty()) errors.rejectValue("message", "form.upload.empty");
        if (errors.hasErrors()) return accessClassroom(classId, form);
        Lecture currentLecture = checkLectureExistence(classId);
        User currentUser = checkCurrentUser();
        checkAccessToClassroom(currentUser, currentLecture);
        Optional<Post> maybePost = postService.post(currentUser.getId(), classId, form.getFile().getOriginalFilename(), form.getFile().getBytes(), form.getMessage(), form.getFile().getContentType());
        if (!maybePost.isPresent()) throw new OperationFailedException("exception.failed");
        String localAddress = localAddress(request, "/classroom");
        try {
            emailService.sendNewPostMessage(currentUser, currentLecture, localAddress);
        } catch (RuntimeException exception) {
            throw new OperationFailedException("exception");
        }
        return new ModelAndView("redirect:/classroom/" + classId);
    }

    private String localAddress(HttpServletRequest request, String path) {
        String url;
        URL requestURL;
        try {
            requestURL = new URL(request.getRequestURL().toString());
        } catch (MalformedURLException exception) {
            throw new OperationFailedException("exception.failed");
        }
        url = requestURL.toString();
        int index = url.indexOf(path);
        return url.substring(0, index);
    }

    private User checkCurrentUser() {
        Optional<User> maybeUser = userService.getCurrentUser();
        if (!maybeUser.isPresent())
            throw new NoUserLoggedException("exception.not.logger.user");
        return maybeUser.get();
    }

    private Lecture checkLectureExistence(Long classId) {
        Optional<Lecture> maybeClass = lectureService.findById(classId);
        if (!maybeClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + classId);
        }
        return maybeClass.get();
    }

    private void checkAccessToClassroom(User currentUser, Lecture lecture) {
        if (!(currentUser.getId().equals(lecture.getTeacher().getId()) || currentUser.getId().equals(lecture.getStudent().getId()))) {
            throw new ClassNotFoundException("No class found for class id " + lecture.getClassId());
        }
    }

    @RequestMapping(value = "/classroom/{classId}", method = RequestMethod.POST, params = "sharedFiles")
    public ModelAndView shareFile(@PathVariable("classId") final Long classId, Long[] sharedFiles) {
        User currentUser = checkCurrentUser();
        Lecture currentLecture = checkLectureExistence(classId);
        checkAccessToClassroom(currentUser, currentLecture);
        for(Long fileId : sharedFiles) {
            lectureService.addSharedFileToLecture(fileId,classId);
        }
        return new ModelAndView("redirect:/classroom/" + classId);
    }

    @RequestMapping(value = "/classroom/{classId}", method = RequestMethod.POST, params = "filesToStopSharing")
    public ModelAndView stopSharingFiles(@PathVariable("classId") final Long classId, @RequestParam("filesToStopSharing") Long[] filesToStopSharing) {
        User currentUser = checkCurrentUser();
        Lecture currentLecture = checkLectureExistence(classId);
        checkAccessToClassroom(currentUser, currentLecture);
        for(Long fileId : filesToStopSharing) {
            lectureService.stopSharingFileInLecture(fileId, classId);
        }
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

    @RequestMapping(value = "/classFile/{classId}/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> openTeacherFile(@PathVariable("classId") final Long classId, @PathVariable("fileId") final Long fileId) {
        SubjectFile subjectFile = subjectFileService.getSubjectFileById(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.add("Content-Disposition", "inline; filename=" + subjectFile.getFileName());

        return new ResponseEntity<>(subjectFile.getFile(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.GET)
    public ModelAndView contactForm(@ModelAttribute("contactForm") final ContactForm form, @PathVariable("uid") final Long uid) {
        final ModelAndView mav = new ModelAndView("contactForm");
        Optional<User> maybeUser = userService.findById(uid);
        User currentUser = checkCurrentUser();
        if (!maybeUser.isPresent() || maybeUser.get().getId().equals(currentUser.getId())) {
            throw new InvalidOperationException("exception.invalid");
        }
        LOGGER.debug("User {} contacting teacher {}", currentUser.getId(), uid);
        mav.addObject("user", maybeUser.get());
        List<SubjectInfo> subjectsGiven = teachesService.getSubjectInfoListByUser(uid);
        List<SubjectInfo> subjectNames = new ArrayList<>(subjectsGiven);
        Set<String> unique = new HashSet<>();
        subjectNames.removeIf(e -> !unique.add(e.getName()));
        mav.addObject("names", subjectNames);
        mav.addObject("subjects", subjectsGiven);
        mav.addObject("currentUid", currentUser.getId());
        return mav;
    }

    @RequestMapping(value = "/contact/{uid}", method = RequestMethod.POST)
    public ModelAndView contact(@PathVariable("uid") final Long uid, @ModelAttribute("contactForm") @Valid final ContactForm form,
                                final BindingResult errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return contactForm(form, uid);
        }
        User currentUser = checkCurrentUser();
        Optional<User> user = userService.findById(uid);
        Optional<Teaches> t = teachesService.findByUserAndSubjectAndLevel(uid, Long.valueOf(form.getSubject()), Integer.parseInt(form.getLevel())%10);
        if (!t.isPresent() || !user.isPresent() || user.get().getId().equals(currentUser.getId())) {
            throw new InvalidOperationException("exception.invalid");
        }
        Optional<Lecture> newLecture = lectureService.create(currentUser.getId(), uid, t.get().getLevel(), t.get().getSubject().getSubjectId(), t.get().getPrice());
        if (!newLecture.isPresent()) throw new OperationFailedException("exception.failed");
        String localAddress = localAddress(request, "/contact");
        try {
            emailService.sendNewClassMessage(user.get().getMail(), currentUser.getName(), t.get().getSubject().getName(), localAddress);
        } catch (RuntimeException exception) {
            throw new OperationFailedException("exception");
        }
        LOGGER.debug("User {} requested class from teacher {}", currentUser.getId(), uid);
        return new ModelAndView("redirect:/classroom/" + newLecture.get().getClassId().toString());
    }
}