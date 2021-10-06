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
        String toFormat = "<p>" + from.get().getName() + " ha aceptado tu pedido de clase de " + subject.get().getName() + ".</p>" +
                "<p>Su mensaje para vos es:</p><p>" + message + "</p><p>Su email es: " + from.get().getMail() + "</p><p>Contáctate con tu profesor para coordinar horarios y modalidades de la clase!</p>";
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
        StringBuilder toFormat = new StringBuilder ("<p>");
        String text;
        switch (myStatus) {
            case 2:
                toFormat.append("Ha finalizado tu clase de ").append(mySubject.getName()).append(" con ").append(teacher.getName()).append("</p><p>").append("Entra a GetAProff para darle un rating!</p>");
                text = String.format(templateMailMessage.getText(), toFormat.toString(), "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                sendSimpleMessage(student.getMail(),"GetAProff: Clase finalizada", text);
                break;
            case 3:
                toFormat.append("El alumno ").append(student.getName()).append(" ha cancelado la clase de ").append(mySubject.getName()).append("</p><p>").append("Entra a GetAProff para ver que otras clases tienes!</p>");
                text = String.format(templateMailMessage.getText(), toFormat.toString(), "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                sendSimpleMessage(teacher.getMail(),"GetAProff: Clase cancelada", text);
                break;
            case 4:
                toFormat.append("Tu profesor ").append(teacher.getName()).append(" ha cancelado tu clase de ").append(mySubject.getName()).append("</p><p>").append("Entra a GetAProff para poder pedir otras clases!</p>");
                text = String.format(templateMailMessage.getText(), toFormat.toString(), "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                sendSimpleMessage(student.getMail(),"GetAProff: Clase cancelada", text);
                break;
            case 5:
                toFormat.append("El profesor ").append(teacher.getName()).append(" ha rechazado tu pedido de clase de ").append(mySubject.getName()).append("</p><p>").append("Entra a GetAProff para poder pedir otras clases!</p>");
                text = String.format(templateMailMessage.getText(), toFormat.toString(), "http://pawserver.it.itba.edu.ar/paw-2021b-6/myClasses","GetAProff/misClases");
                sendSimpleMessage(student.getMail(),"GetAProff:Clase rechazada", text);
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
        StringBuilder toFormat = new StringBuilder ("<p>");
        toFormat.append("Tu alumno ").append(student.get().getName()).append(" ha calificado tu clase de ").append(mySubject.get().getName())
                .append("</p><p>Su calificación fue de: ").append(rating).append(" estrellas").append("</p><p>Su reseña fue:</p><p>")
                .append(review).append("</p><p>").append("Entra a GetAproff para ver tu calificación general!</p>");
        String text = String.format(templateMailMessage.getText(), toFormat.toString(), "http://pawserver.it.itba.edu.ar/paw-2021b-6/profile/"+ String.valueOf(teacher.get().getId()),"GetAProff/misClases");
        sendSimpleMessage(teacher.get().getMail(),"GetAProff:Nueva calificación", text);
    }

    @Override
    @Async
    public void sendSubjectRequest(int uid, String subject, String message) {
        sendSimpleMessage("getaproff@gmail.com","Nueva petición de Materia, uid:" + String.valueOf(uid),"<p>Materia:" +
                subject + "</><p>Razón: " +
                message + "</p>");
    }


}
