package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@EnableAsync
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SimpleMailMessage templateMailMessage;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
            javaMailSender.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void sendContactMessage(String to, String mailSubject, String userFrom, String subject, String message) {
        String text = String.format(templateMailMessage.getText(), "Hola que tal, desde GetAproff el usuario",userFrom, "pide por tus clases de",subject,"","", message,"Entra a GetAProff para aceptar o rechazar su solicitud!");
        sendSimpleMessage(to,mailSubject, text);
    }

    @Override
    @Async
    public void sendAcceptMessage(String to, String mailSubject, String userFrom, String subject,String mailFrom, String message) {
        String text = String.format(templateMailMessage.getText(),userFrom," ha aceptado tu pedido de clases de"," ",subject,"su email es",mailFrom, message,"Contáctate con tu profesor para coordinar horarios y modalidades de la clase!");
        sendSimpleMessage(to,mailSubject, text);
    }
}
