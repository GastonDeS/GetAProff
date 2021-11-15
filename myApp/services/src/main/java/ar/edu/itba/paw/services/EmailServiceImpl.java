package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.MailNotSentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@EnableAsync
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SimpleMailMessage templateMailMessage;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LectureService lectureService;


    private void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new MailNotSentException("Unable to send mail");
        }
    }

    @Override
    @Async
    public void sendNewClassMessage(String to, String userFrom, String subject, String localAddr) {
        String mailSubject = messageSource.getMessage("mail.subject.new.class", null, LocaleContextHolder.getLocale());
        String toFormat = messageSource.getMessage("mail.subject.new.class.body", new Object[] {userFrom, subject}, LocaleContextHolder.getLocale());
        String button = messageSource.getMessage("mail.subject.new.class.btn",null, LocaleContextHolder.getLocale());
        String text = String.format(templateMailMessage.getText(), mailSubject,toFormat, localAddr + "/myClasses/offered/0", button);
        sendSimpleMessage(to,mailSubject, text);

    }

    @Override
    @Async
    public void sendStatusChangeMessage(Lecture myLecture, String localAddr) {
        Optional<User> maybeS = userService.findById(myLecture.getStudent().getId());
        Optional<User> maybeT = userService.findById(myLecture.getTeacher().getId());
        Optional<Subject> maybeSub = subjectService.findById(myLecture.getSubject().getId());
        if (!maybeS.isPresent() || !maybeT.isPresent() || !maybeSub.isPresent()) {
            throw new NoSuchElementException();
        }
        int myStatus = myLecture.getStatus();
        Subject mySubject = maybeSub.get();
        User student = maybeS.get();
        User teacher = maybeT.get();
        String text;
        String mailSubject;
        String format;
        String button;
        switch (myStatus) {
            case 1:
                format = messageSource.getMessage("mail.class.accepted.body", new Object[] {teacher.getName(), mySubject.getName(), teacher.getMail()}, LocaleContextHolder.getLocale());
                mailSubject = messageSource.getMessage("mail.class.accepted", null, LocaleContextHolder.getLocale());
                button = messageSource.getMessage("mail.class.accepted.btn", null, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), mailSubject, format, localAddr + "/classroom/" + myLecture.getClassId().toString(),button);
                sendSimpleMessage(student.getMail(),mailSubject, text);
                break;
            case 2:
                format = messageSource.getMessage("mail.class.finished.body", new Object[] {mySubject.getName(), teacher.getName()}, LocaleContextHolder.getLocale());
                mailSubject = messageSource.getMessage("mail.class.finished", null, LocaleContextHolder.getLocale());
                button = messageSource.getMessage("mail.class.finished.btn", null, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), mailSubject, format, localAddr + "/classroom/" + myLecture.getClassId().toString(),button);
                sendSimpleMessage(student.getMail(),mailSubject, text);
                break;
            case 3:
                format = messageSource.getMessage("mail.class.student.cancelled.body", new Object[] {student.getName(), mySubject.getName()}, LocaleContextHolder.getLocale());
                mailSubject = messageSource.getMessage("mail.class.cancelled", null, LocaleContextHolder.getLocale());
                button = messageSource.getMessage("mail.class.student.cancelled.btn", null, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), mailSubject, format, localAddr + "/myClasses/offered/2",button);
                sendSimpleMessage(teacher.getMail(),mailSubject, text);
                break;
            case 4:
                format = messageSource.getMessage("mail.class.teacher.cancelled.body", new Object[] {teacher.getName(), mySubject.getName()}, LocaleContextHolder.getLocale());
                button = messageSource.getMessage("mail.class.teacher.cancelled.btn", null, LocaleContextHolder.getLocale());
                mailSubject = messageSource.getMessage("mail.class.cancelled", null, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), mailSubject,format, localAddr + "/myClasses/requested/2",button);
                sendSimpleMessage(student.getMail(),mailSubject, text);
                break;
            case 5:
                format = messageSource.getMessage("mail.class.rejected.body", new Object[] {teacher.getName(), mySubject.getName()}, LocaleContextHolder.getLocale());
                button = messageSource.getMessage("mail.class.rejected.btn", null, LocaleContextHolder.getLocale());
                mailSubject = messageSource.getMessage("mail.class.rejected", null, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), mailSubject, format, localAddr + "/myClasses/requested/2",button);
                sendSimpleMessage(student.getMail(),mailSubject, text);
                break;
        }
    }

    @Override
    @Async
    public void sendRatedMessage(Lecture myLecture, int rating, String review, String localAddr) {
        Optional<User> student = userService.findById(myLecture.getStudent().getId());
        Optional<User> teacher = userService.findById(myLecture.getTeacher().getId());
        Optional<Subject> mySubject = subjectService.findById(myLecture.getSubject().getId());
        if (!student.isPresent() || !teacher.isPresent() || !mySubject.isPresent()) {
            throw new NoSuchElementException();
        }
        String toFormat = messageSource.getMessage("mail.subject.rated.body", new Object[] {
                student.get().getName(), mySubject.get().getName(), rating, review}, LocaleContextHolder.getLocale());
        String mailSubject = messageSource.getMessage("mail.subject.rated", null, LocaleContextHolder.getLocale());
        String button = messageSource.getMessage("mail.subject.rated.btn",null, LocaleContextHolder.getLocale());
        String text = String.format(templateMailMessage.getText(), mailSubject, toFormat, localAddr + "/profile/"+ teacher.get().getId(),button);
        sendSimpleMessage(teacher.get().getMail(),mailSubject, text);
    }

    @Override
    @Async
    public void sendSubjectRequest(Long uid, String subject, String message) {
        String mailSubject = messageSource.getMessage("mail.subject.request", new Object[] {subject}, LocaleContextHolder.getLocale());
        String text = messageSource.getMessage("mail.subject.request.body", new Object[] {subject, message}, LocaleContextHolder.getLocale());
        sendSimpleMessage("getaproff@gmail.com", mailSubject,text);
    }

    @Override
    @Async
    public void sendNewPostMessage(User poster, Lecture lecture, String localAddr) {
        String to;
        String toFormat;
        int status = 1;
        if (lecture.getStudent().equals(poster)) {
            to = lecture.getTeacher().getMail();
            status = 0;
        } else {
            to = lecture.getStudent().getMail();
        }
        String mailSubject = messageSource.getMessage("mail.class.new.post.subject", null, LocaleContextHolder.getLocale());
        if (status == 0){
            toFormat = messageSource.getMessage("mail.class.new.student.post.body", new Object[] {poster.getName(), lecture.getSubject().getName()}, LocaleContextHolder.getLocale());
        } else  {
            toFormat = messageSource.getMessage("mail.class.new.teacher.post.body", new Object[] {poster.getName(), lecture.getSubject().getName()}, LocaleContextHolder.getLocale());
        }
        String button = messageSource.getMessage("mail.class.new.post.btn",null, LocaleContextHolder.getLocale());
        String text = String.format(templateMailMessage.getText(), mailSubject,toFormat, localAddr + "/classroom/" + lecture.getClassId(), button);
        sendSimpleMessage(to,mailSubject, text);
    }

}
