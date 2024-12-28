package com.example.final_project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendAppointmentConfirmation(String to, Map<String, Object> templateModel) {
        try {
            String html = templateEngine.process("appointment-confirmation", new Context(null, templateModel));
            sendHtmlEmail(to, "Appointment Confirmation", html);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send confirmation email", e);
        }
    }

    public void sendAppointmentCancellation(String to, Map<String, Object> templateModel) {
        try {
            String html = templateEngine.process("appointment-cancellation", new Context(null, templateModel));
            sendHtmlEmail(to, "Appointment Cancellation", html);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send cancellation email", e);
        }
    }

    private void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }
}