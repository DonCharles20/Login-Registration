package com.don.login_registration.email;

/**
 * This interface is used to send email to the user for email verification.
 * It is implemented by the EmailService class
 * */
public interface EmailSender {
    void send(String to, String email);
}
