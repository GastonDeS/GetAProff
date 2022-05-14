package ar.edu.itba.paw.webapp.security.voters;

import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.interfaces.services.PostService;
import ar.edu.itba.paw.interfaces.services.RatingService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.security.api.models.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.models.PawUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

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

    private Long getUserId(Authentication authentication) {
        return getUser(authentication).getUserid();
    }

    private boolean isTeacher(Authentication authentication) {
        return getUser(authentication).isTeacher();
    }

    private User getUser(Authentication authentication) {
        if(authentication instanceof BasicAuthenticationToken) {
            return userService.findByEmail(((BasicAuthenticationToken)authentication).getPrincipal()).orElseThrow(NoSuchElementException::new);
        }
        return ((PawUser)(authentication.getPrincipal())).toUser();
    }

//    public boolean permitAll(Authentication authentication) {
//        return true;
//    }

    public boolean canAccessClassroom(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Optional<Lecture> lecture = lectureService.findById(id);
        if (!lecture.isPresent()) throw new ClassNotFoundException("");
        Long loggedUserId = getUserId(authentication);
        return lecture.get().getTeacher().getUserid().equals(loggedUserId) || lecture.get().getStudent().getUserid().equals(loggedUserId);
    }

    public boolean canAccessClassroomAsTeacher(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Optional<Lecture> lecture = lectureService.findById(id);
        if (!lecture.isPresent()) throw new ClassNotFoundException("");
        Long loggedUserId = getUserId(authentication);
        return lecture.get().getTeacher().getUserid().equals(loggedUserId);
    }

    public boolean canAccessPostFile(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Lecture lecture = postService.getFileData(id).getAssociatedLecture(); //TODO add optional
//        if (!post.isPresent()) throw new ClassNotFoundException("");
        Long loggedUserId = getUserId(authentication);
        return lecture.getTeacher().getUserid().equals(loggedUserId) || lecture.getStudent().getUserid().equals(loggedUserId);
    }

    public boolean canAccessWithSameId(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return id.equals(getUserId(authentication));
    }

    public boolean canRate(Authentication authentication, Long tid) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return ratingService.availableToRate(tid, getUserId(authentication));
    }
}

