package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

@EnableWebMvc
@ComponentScan({"ar.edu.itba.paw.webapp.controller", "ar.edu.itba.paw.services"})
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
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage contactSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "<p>%s %s %s %s!</p>" +
                        "<p>%s %s</p>" +
                        "<p>Te escribió este mensaje:</p>" +
                        "<p>%s</p>" +
                        "<p>%s<p>");
        return message;
    }
}
