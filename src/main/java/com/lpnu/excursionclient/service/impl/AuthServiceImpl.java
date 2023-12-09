package com.lpnu.excursionclient.service.impl;

import com.lpnu.excursionclient.dto.request.auth.*;
import com.lpnu.excursionclient.enums.client.EClientStatus;
import com.lpnu.excursionclient.exception.*;
import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.model.client.ClientActivationToken;
import com.lpnu.excursionclient.model.client.ClientRefreshToken;
import com.lpnu.excursionclient.model.client.ClientResetPasswordToken;
import com.lpnu.excursionclient.repository.client.*;
import com.lpnu.excursionclient.security.JwtMethods;
import com.lpnu.excursionclient.security.JwtPair;
import com.lpnu.excursionclient.service.AuthService;
import com.lpnu.excursionclient.service.SmtpMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientRepository clientRepository;
    private final ClientStatusRepository clientStatusRepository;
    private final ClientActivationTokenRepository clientActivationTokenRepository;
    private final ClientRefreshTokenRepository clientRefreshTokenRepository;
    private final ClientResetPasswordTokenRepository clientResetPasswordTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtMethods jwtMethods;
    private final SmtpMailSender smtpMailSender;

    @Override
    public void register(RegisterRequest registerRequest, String originUrl) {
        if (clientRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        Client client = Client.of(
                registerRequest.firstName(),
                registerRequest.lastName(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.email(),
                clientStatusRepository.findByName(EClientStatus.NOT_ACTIVATED.getName())
                        .orElseThrow(InvalidClientStatusNameException::new)
        );

        sendNewActivationToken(client, originUrl);
    }

    @Override
    public void sendActivationTokenAgain(ActivationTokenAgainRequest activationTokenAgainRequest, String originUrl) {
        Client client = clientRepository.findById(UUID.fromString(activationTokenAgainRequest.clientId()))
                .orElseThrow(InvalidClientIdException::new);

        if (client.getClientStatus().getName().equals(EClientStatus.BLOCKED.getName())) {
            throw new ClientBlockedException();
        }

        sendNewActivationToken(client, originUrl);
    }

    @Override
    public JwtPair activate(ActivateRequest activateRequest) {
        Client client = clientActivationTokenRepository.findByToken(activateRequest.activationToken())
                .orElseThrow(InvalidActivationTokenException::new).getClient();

        if (client.getClientStatus().getName().equals(EClientStatus.BLOCKED.getName())) {
            throw new ClientBlockedException();
        }

        client.setClientStatus(clientStatusRepository.findByName(EClientStatus.ACTIVE.getName())
                .orElseThrow(InvalidClientStatusNameException::new));
        client.getClientActivationTokens().clear();

        JwtPair jwtPair = jwtMethods.getJwtPair(client.getEmail());
        saveRefreshJwt(jwtPair.refreshJwt().token(), client);
        return jwtPair;
    }

    @Override
    public JwtPair login(LoginRequest loginRequest) {
        Client client = clientRepository.findByEmail(loginRequest.email())
                .orElseThrow(InvalidClientEmailOrPasswordException::new);

        if (!passwordEncoder.matches(loginRequest.password(), client.getPassword())) {
            throw new InvalidClientEmailOrPasswordException();
        }

        checkClientStatus(client);
        JwtPair jwtPair = jwtMethods.getJwtPair(loginRequest.email());
        saveRefreshJwt(jwtPair.refreshJwt().token(), client);
        return jwtPair;
    }

    @Override
    public void logout(String refreshToken) {
        clientRefreshTokenRepository.delete(clientRefreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new));
    }

    @Override
    public void forgot(String email, String originUrl) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(InvalidEmailException::new);

        ClientResetPasswordToken clientResetPasswordToken = ClientResetPasswordToken.of(client);
        client.getClientResetPasswordTokens().add(clientResetPasswordToken);
        clientRepository.save(client);

        smtpMailSender.sendResetPasswordMessage(client.getEmail(), clientResetPasswordToken.getToken(), originUrl);
    }

    @Override
    public void reset(ResetPasswordRequest resetPasswordRequest) {
        Client client = clientResetPasswordTokenRepository.findByToken(resetPasswordRequest.resetPasswordToken())
                .orElseThrow(InvalidResetPasswordTokenException::new).getClient();

        client.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        client.getClientResetPasswordTokens().clear();
        clientRepository.save(client);
    }

    @Override
    public JwtPair refresh(String refreshJwt) {
        if (jwtMethods.isRefreshJwtExpired(refreshJwt)) {
            throw new InvalidRefreshTokenException();
        }

        ClientRefreshToken clientRefreshToken = clientRefreshTokenRepository.findByToken(refreshJwt)
                .orElseThrow(InvalidRefreshTokenException::new);

        Client client = clientRefreshToken.getClient();
        client.getClientRefreshTokens().remove(clientRefreshToken);
        checkClientStatus(client);

        JwtPair jwtPair = jwtMethods.getJwtPair(client.getEmail());
        saveRefreshJwt(jwtPair.refreshJwt().token(), client);
        return jwtPair;
    }

    private void checkClientStatus(Client client) {
        if (client.getClientStatus().getName().equals(EClientStatus.BLOCKED.getName())) {
            throw new ClientBlockedException();
        }
    }

    @Override
    public void sendNewActivationToken(Client client, String originUrl) {
        ClientActivationToken clientActivationToken = ClientActivationToken.of(client);
        client.getClientActivationTokens().add(clientActivationToken);
        clientRepository.save(client);

        smtpMailSender.sendActivateAccountMessage(client.getEmail(), clientActivationToken.getToken(), originUrl);
    }

    private void saveRefreshJwt(String refreshJwt, Client client) {
        client.getClientRefreshTokens().add(ClientRefreshToken.of(
                refreshJwt,
                jwtMethods.extractIssuedAtFromRefreshJwt(refreshJwt),
                jwtMethods.extractExpiredAtFromRefreshJwt(refreshJwt),
                client
        ));
        clientRepository.save(client);
    }
}
