package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SimpleMailMessage templateMailMessage;

    @Override
    @Async
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
    public void sendTemplateMessage(String to, String subject,String mailFrom, String message) {
        String text = String.format(templateMailMessage.getText(),mailFrom, message);
        sendSimpleMessage(to,subject, text);
    }
}
