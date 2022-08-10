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
                "<table align=\"center\" style=\"background-color:#9fedd7; border-radius: 10px;\">" +
                        "<tr>" +
                        "<td align=\"center\">" +
                        "<h1 style=\"color:#026670;\">%s</h1>" +
                "<table style=\"background-color:#ffffff; border-radius: 10px;\">" +
                        "<tr>" +
                        "<td align=\"center\">" +
                        "<pstyle=\"color:black;\">%s</p>" +
                        "</td>" +
                        "</tr>" +
                "</table>" +
                        "<form action=\"%s\">" +
                        "    <input type=\"submit\" style=\" border-radius: 10px; background-color: #026670; color: white; margin-top: 10px; margin-bottom: 10px; width: fit-content; height: 30px; font-size: 16px; border-color: transparent;\" value=\"%s\" />" +
                        "</form>" +
                "</td>" +
                        "</tr>" +
                        "</table>");
        return message;
    }
}
