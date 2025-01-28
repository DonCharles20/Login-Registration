package com.don.login_registration.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

/**
 * This class is used to send email to the user for email verification.
 * It implements the EmailSender interface
 * */
@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final static Logger LOGGER =LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    

    @Override
    @Async//this annotation is used to run the method in a different thread
    public void send(String to, String email) {//
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("hello@don.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            
            LOGGER.error("failed to send email",e);
            throw new IllegalStateException("failed to send email");
        }
    }

}
