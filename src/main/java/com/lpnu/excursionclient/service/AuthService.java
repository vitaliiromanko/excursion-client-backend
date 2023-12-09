package com.lpnu.excursionclient.service;

import com.lpnu.excursionclient.dto.request.auth.*;
import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.security.JwtPair;

public interface AuthService {
    void register(RegisterRequest registerRequest, String originUrl);

    void sendActivationTokenAgain(ActivationTokenAgainRequest activationTokenAgainRequest, String originUrl);

    JwtPair activate(ActivateRequest activateRequest);

    JwtPair login(LoginRequest loginRequest);

    void logout(String refreshToken);

    void forgot(String email, String originUrl);

    void reset(ResetPasswordRequest resetPasswordRequest);

    JwtPair refresh(String refreshToken);

    void sendNewActivationToken(Client client, String originUrl);
}
