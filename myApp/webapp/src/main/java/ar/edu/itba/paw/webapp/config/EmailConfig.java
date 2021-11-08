package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("getaproff@gmail.com");
        mailSender.setPassword("pass1234word");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "<table align=\"center\" style=\"background-color:#9fedd7; witdh:600px;\">" +
                        "<tr>" +
                        "<td align=\"center\">" +
                        "<h1>%s</h1>" +
                "<table style=\"background-color:#ffffff;\">" +
                        "<tr>" +
                        "<td align=\"center\">" +
                        "<p>%s</p>" +
                        "<a href=\"%s\" style=\"color: #ffffff; text-decoration: none; background-color: #026670; text-decoration: none; padding: 15px 25px; display: inline-block;\">%s</a>" +
                        "</td>" +
                        "</tr>" +
                "</table>" +
                "</td>" +
                        "</tr>" +
                        "</table>");
        return message;
    }
}
