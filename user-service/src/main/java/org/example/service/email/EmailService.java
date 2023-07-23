package org.example.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    //TODO: get that from config file
    private final String EMAIL_FROM = "examplemail@company.com";

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm email");
            helper.setFrom(EMAIL_FROM);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send confirmation email");

            throw new IllegalStateException("Failed to send confirmation email");
        }
    }

}
