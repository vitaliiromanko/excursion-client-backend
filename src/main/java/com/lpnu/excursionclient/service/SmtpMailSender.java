package com.lpnu.excursionclient.service;

public interface SmtpMailSender {
    void sendActivateAccountMessage(String emailTo, String activateToken, String baseUrl);

    void sendResetPasswordMessage(String emailTo, String resetToken, String baseUrl);

    void sendCancelOrderMessage(String emailTo, String orderId, String reason);
}
