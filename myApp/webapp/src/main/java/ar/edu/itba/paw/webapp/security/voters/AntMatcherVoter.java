package ar.edu.itba.paw.webapp.security.voters;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserFile;
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

    @Autowired
    private SubjectFileService subjectFileService;

    @Autowired
    UserFileService userFileService;

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

    public boolean canAccessDeleteSubjectFile(Authentication authentication, Long id){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        SubjectFile subjectFile = subjectFileService.getSubjectFileById(id);
        return subjectFile.getTeachesInfo().getTeacher().getId().equals(getUserId(authentication));
    }

    public boolean canAccessDeleteCertification(Authentication authentication, Long id){
        if (authentication instanceof AnonymousAuthenticationToken) return false;
        Optional<UserFile> certification = userFileService.getFileById(id);
        if (!certification.isPresent()) throw new ClassNotFoundException(""); //TODO create this exception
        return certification.get().getFileOwner().getId().equals(getUserId(authentication));
    }

    public boolean canAccessWithSameId(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return id.equals(getUserId(authentication));
    }

    public boolean canRate(Authentication authentication, Long teacherId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return ratingService.availableToRate(teacherId, getUserId(authentication));
    }
}

