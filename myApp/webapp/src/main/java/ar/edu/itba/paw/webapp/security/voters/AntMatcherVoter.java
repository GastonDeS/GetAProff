package ar.edu.itba.paw.webapp.security.voters;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.security.api.models.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.models.PawUser;
import ar.edu.itba.paw.webapp.util.NotFoundStatusMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class AntMatcherVoter {

    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private PostService postService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private SubjectFileService subjectFileService;

    @Autowired
    private UserFileService userFileService;


    private Long getUserId(Authentication authentication) {
        return getUser(authentication).getUserid();
    }

    private boolean isTeacher(Authentication authentication) {
        return getUser(authentication).isTeacher();
    }

    private User getUser(Authentication authentication) {
        if(authentication instanceof BasicAuthenticationToken) {
            return userService.findByEmail(((BasicAuthenticationToken)authentication).getPrincipal()).orElseThrow(NoUserLoggedException::new);
        }
        return ((PawUser)(authentication.getPrincipal())).toUser();
    }

    public boolean canAccessClassroom(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Lecture lecture = lectureService.findById(id).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CLASS));
        Long loggedUserId = getUserId(authentication);
        return lecture.getTeacher().getUserid().equals(loggedUserId) || lecture.getStudent().getUserid().equals(loggedUserId);
    }

    public boolean canAccessClassroomAsTeacher(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Lecture lecture = lectureService.findById(id).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CLASS));
        Long loggedUserId = getUserId(authentication);
        return lecture.getTeacher().getUserid().equals(loggedUserId);
    }

    public boolean canAccessPostFile(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Lecture lecture = postService.getPost(id).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.POST))
                .getAssociatedLecture();
        Long loggedUserId = getUserId(authentication);
        return lecture.getTeacher().getUserid().equals(loggedUserId) || lecture.getStudent().getUserid().equals(loggedUserId);
    }

    public boolean canAccessDeleteSubjectFile(Authentication authentication, Long id){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        SubjectFile subjectFile = subjectFileService.getSubjectFileById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.SUBJECT_FILE));
        return subjectFile.getTeachesInfo().getTeacher().getId().equals(getUserId(authentication));
    }

    public boolean canAccessGetSubjectFile(Authentication authentication, Long id){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        SubjectFile subjectFile = subjectFileService.getSubjectFileById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.SUBJECT_FILE));
        return subjectFile.getTeachesInfo().getTeacher().getId().equals(getUserId(authentication));
    }

    public boolean canAccessDeleteCertification(Authentication authentication, Long id){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        UserFile certification = userFileService.getFileById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CERTIFICATION));
        return certification.getFileOwner().getId().equals(getUserId(authentication));
    }

    public boolean canAccessWithSameId(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return id.equals(getUserId(authentication));
    }

    public boolean canRate(Authentication authentication, Long teacherId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return ratingService.availableToRate(teacherId, getUserId(authentication));
    }

    public boolean canRequestClass(Authentication authentication, Long teacherId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        User teacher = userService.findById(teacherId).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.USER));
        return !teacher.isTeacher() && teacherId.equals(getUserId(authentication));
    }

    public boolean canAccessRate(Authentication authentication, Long teacherId, Long classId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Lecture lecture = lectureService.findById(classId).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.CLASS));
        return ratingService.availableToRate(teacherId, getUserId(authentication));
    }

    public boolean canAccessProfile(Authentication authentication, Long uid) {
        User user = userService.findById(uid).orElseThrow(() -> new NotFoundException(NotFoundStatusMessages.USER));
        if (user.isTeacher()) return true;
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        return uid.equals(getUserId(authentication));
    }

}

