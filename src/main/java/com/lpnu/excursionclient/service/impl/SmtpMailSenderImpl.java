package com.lpnu.excursionclient.service.impl;

import com.lpnu.excursionclient.exception.SendMessageException;
import com.lpnu.excursionclient.service.SmtpMailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class SmtpMailSenderImpl implements SmtpMailSender {
    private final JavaMailSender javaMailSender;
    private final String defaultFrontendUrl;
    private final String senderEmail;
    private final boolean isSenderActive;

    public SmtpMailSenderImpl(
            JavaMailSender javaMailSender,
            @Value("${application.frontend.default-url}") String defaultFrontendUrl,
            @Value("${application.sender.email}") String senderEmail,
            @Value("${application.sender.active}") boolean isSenderActive
    ) {
        this.javaMailSender = javaMailSender;
        this.defaultFrontendUrl = defaultFrontendUrl;
        this.senderEmail = senderEmail;
        this.isSenderActive = isSenderActive;
    }

    @Override
    public void sendActivateAccountMessage(String emailTo, String activateToken, String baseUrl) {
        sendMessage(
                emailTo,
                "Активація облікового запису",
                String.format("Перейдіть за посиланням, щоб активувати ваш обліковий запис:\n%s/activate/%s",
                        getBaseUrl(baseUrl), activateToken)
        );
    }

    @Override
    public void sendResetPasswordMessage(String emailTo, String resetToken, String baseUrl) {
        sendMessage(
                emailTo,
                "Відновлення паролю",
                String.format("Перейдіть за посиланням, щоб відновити ваш пароль:\n%s/reset/%s",
                        getBaseUrl(baseUrl), resetToken)
        );
    }

    @Override
    public void sendCancelOrderMessage(String emailTo, String orderId, String reason) {
        if (reason == null) {
            sendMessage(
                    emailTo,
                    "Клієнт скасував замовлення",
                    String.format("""
                            Користувач відмовився від замовлення: %s""", orderId)
            );
        } else {
            sendMessage(
                    emailTo,
                    "Клієнт скасував замовлення",
                    String.format("""
                            Користувач відмовився від замовлення: %s
                                                        
                            По причині:
                            %s""", orderId, reason)

            );
        }
    }

    private String getBaseUrl(String baseUrl) {
        return baseUrl != null ? baseUrl : defaultFrontendUrl;
    }

    private void sendMessage(String emailTo, String subject, String text) {
        if (isSenderActive) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setFrom(new InternetAddress(senderEmail, "My Excursion"));
                helper.setTo(emailTo);
                helper.setSubject(subject);
                helper.setText(text);
                javaMailSender.send(message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new SendMessageException();
            }
        }
    }
}
