package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.SubjectService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Class;
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

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
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
    public void sendContactMessage(String to, String userFrom, String subject, String message) {
        String mailSubject = messageSource.getMessage("mail.subject.new.class", null, LocaleContextHolder.getLocale());
        String toFormat = messageSource.getMessage("mail.subject.new.class.body", new Object[] {userFrom, subject, message}, LocaleContextHolder.getLocale());
        String text = String.format(templateMailMessage.getText(), toFormat, "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
        sendSimpleMessage(to,mailSubject, text);
    }

    @Override
    @Async
    public void sendAcceptMessage(int toId, int fromId, int sid, String message) {
        String mailSubject = messageSource.getMessage("mail.subject.accept.class", null, LocaleContextHolder.getLocale());
        Optional<User> to = userService.findById(toId);
        Optional<User> from = userService.findById(fromId);
        Optional<Subject> subject = subjectService.findById(sid);
        if (!to.isPresent() || !from.isPresent() || !subject.isPresent()) {
            throw new NoSuchElementException();
        }
        String toFormat = messageSource.getMessage("mail.subject.accept.class.body", new Object[] {from.get().getName(), subject.get().getName(), message, from.get().getMail()}, LocaleContextHolder.getLocale());
        String text = String.format(templateMailMessage.getText(), toFormat, "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
        sendSimpleMessage(to.get().getMail(),mailSubject, text);
    }

    @Override
    @Async
    public void sendStatusChangeMessage(Class myClass) {
        Optional<User> maybeS = userService.findById(myClass.getStudentId());
        Optional<User> maybeT = userService.findById(myClass.getTeacherId());
        Optional<Subject> maybeSub = subjectService.findById(myClass.getSubjectid());
        if (!maybeS.isPresent() || !maybeT.isPresent() || !maybeSub.isPresent()) {
            throw new NoSuchElementException();
        }
        int myStatus = myClass.getStatus();
        Subject mySubject = maybeSub.get();
        User student = maybeS.get();
        User teacher = maybeT.get();
        String text;
        String mailSubject;
        String format;
        switch (myStatus) {
            case 2:
                format = messageSource.getMessage("mail.class.finished.body", new Object[] {mySubject.getName(), teacher.getName()}, LocaleContextHolder.getLocale());
                mailSubject = messageSource.getMessage("mail.class.finished", null, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), format, "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                sendSimpleMessage(student.getMail(),mailSubject, text);
                break;
            case 3:
                format = messageSource.getMessage("mail.class.student.cancelled.body", new Object[] {student.getName(), mySubject.getName()}, LocaleContextHolder.getLocale());
                mailSubject = messageSource.getMessage("mail.class.finished", null, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), format, "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                sendSimpleMessage(teacher.getMail(),mailSubject, text);
                break;
            case 4:
                format = messageSource.getMessage("mail.class.teacher.cancelled.body", new Object[] {teacher.getName(), mySubject.getName()}, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), format, "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                mailSubject = messageSource.getMessage("mail.class.finished", null, LocaleContextHolder.getLocale());
                sendSimpleMessage(student.getMail(),mailSubject, text);
                break;
            case 5:
                format = messageSource.getMessage("mail.class.rejected.body", new Object[] {teacher.getName(), mySubject.getName()}, LocaleContextHolder.getLocale());
                text = String.format(templateMailMessage.getText(), format, "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                mailSubject = messageSource.getMessage("mail.class.rejected", null, LocaleContextHolder.getLocale());
                sendSimpleMessage(student.getMail(),mailSubject, text);
                break;
        }
    }

    @Override
    @Async
    public void sendRatedMessage(Class myClass, int rating, String review) {
        Optional<User> student = userService.findById(myClass.getStudentId());
        Optional<User> teacher = userService.findById(myClass.getTeacherId());
        Optional<Subject> mySubject = subjectService.findById(myClass.getSubjectid());
        if (!student.isPresent() || !teacher.isPresent() || !mySubject.isPresent()) {
            throw new NoSuchElementException();
        }
        String toFormat = messageSource.getMessage("mail.subject.rated.body", new Object[] {
                student.get().getName(), mySubject.get().getName(), rating, review}, LocaleContextHolder.getLocale());
        String mailSubject = messageSource.getMessage("mail.subject.rated", null, LocaleContextHolder.getLocale());
        String text = String.format(templateMailMessage.getText(), toFormat, "http://pawserver.it.itba.edu.ar/paw-2021b-6/profile/"+ teacher.get().getId(),"GetAProff/misClases");
        sendSimpleMessage(teacher.get().getMail(),mailSubject, text);
    }

    @Override
    @Async
    public void sendSubjectRequest(int uid, String subject, String message) {
        String mailSubject = messageSource.getMessage("mail.subject.request", new Object[] {subject}, LocaleContextHolder.getLocale());
        String text = messageSource.getMessage("mail.subject.request.body", new Object[] {subject, message}, LocaleContextHolder.getLocale());
        sendSimpleMessage("getaproff@gmail.com", mailSubject,text);
    }


}
