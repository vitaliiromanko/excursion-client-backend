package com.lpnu.excursionclient.service.impl;

import com.lpnu.excursionclient.dto.request.client.ClientUpdateProfilePasswordRequest;
import com.lpnu.excursionclient.dto.request.client.ClientUpdateProfileRequest;
import com.lpnu.excursionclient.dto.response.client.ClientGetProfileResponse;
import com.lpnu.excursionclient.enums.client.EClientStatus;
import com.lpnu.excursionclient.exception.*;
import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.model.client.ClientPhotoData;
import com.lpnu.excursionclient.repository.client.ClientRepository;
import com.lpnu.excursionclient.repository.client.ClientStatusRepository;
import com.lpnu.excursionclient.service.AuthService;
import com.lpnu.excursionclient.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientStatusRepository clientStatusRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String updateProfile(String userEmail, ClientUpdateProfileRequest clientUpdateProfileRequest,
                                String originUrl) {
        Client client = getClient(userEmail);

        boolean isClientUpdated = false;
        boolean isClientEmailUpdated = false;

        if (clientUpdateProfileRequest.clientPhoto() != null) {
            Optional<ClientPhotoData> optionalClientPhotoData = Optional.ofNullable(client.getClientPhotoData());
            if (optionalClientPhotoData.isPresent()) {
                ClientPhotoData clientPhotoData = optionalClientPhotoData.get();
                if (!clientPhotoData.getPhoto().equals(clientUpdateProfileRequest.clientPhoto())) {
                    saveClientPhoto(clientUpdateProfileRequest.clientPhoto(), client);
                    isClientUpdated = true;
                }
            } else {
                saveClientPhoto(clientUpdateProfileRequest.clientPhoto(), client);
                isClientUpdated = true;
            }
        }

        if (!client.getFirstName().equals(clientUpdateProfileRequest.firstName())) {
            client.setFirstName(clientUpdateProfileRequest.firstName());
            isClientUpdated = true;
        }

        if (!client.getLastName().equals(clientUpdateProfileRequest.lastName())) {
            client.setLastName(clientUpdateProfileRequest.lastName());
            isClientUpdated = true;
        }

        if (client.getPatronymic() == null || !client.getPatronymic().equals(clientUpdateProfileRequest.patronymic())) {
            client.setPatronymic(clientUpdateProfileRequest.patronymic());
            isClientUpdated = true;
        }

        if (!client.getEmail().equals(clientUpdateProfileRequest.email())) {
            if (clientRepository.findByEmail(clientUpdateProfileRequest.email()).isPresent()) {
                throw new EmailAlreadyUsedException();
            }

            client.setEmail(clientUpdateProfileRequest.email());
            client.setClientStatus(clientStatusRepository.findByName(EClientStatus.NOT_ACTIVATED.getName())
                    .orElseThrow(InvalidClientStatusNameException::new));
            isClientUpdated = true;
            isClientEmailUpdated = true;
        }

        if (client.getPhoneNumber() == null || !client.getPhoneNumber().equals(clientUpdateProfileRequest.phoneNumber())) {
            client.setPhoneNumber(clientUpdateProfileRequest.phoneNumber());
            isClientUpdated = true;
        }

        if (client.getBirthday() == null || !client.getBirthday().equals(clientUpdateProfileRequest.birthday())) {
            client.setBirthday(clientUpdateProfileRequest.birthday());
            isClientUpdated = true;
        }

        if (isClientEmailUpdated) {
            authService.sendNewActivationToken(client, originUrl);
        } else if (isClientUpdated) {
            clientRepository.save(client);
        }
        return client.getClientStatus().getName();
    }

    @Override
    public void updateProfilePassword(String email,
                                      ClientUpdateProfilePasswordRequest clientUpdateProfilePasswordRequest) {
        Client client = getClient(email);

        if (!passwordEncoder.matches(clientUpdateProfilePasswordRequest.oldPassword(), client.getPassword())) {
            throw new InvalidPasswordException();
        }
        client.setPassword(passwordEncoder.encode(clientUpdateProfilePasswordRequest.newPassword()));
        clientRepository.save(client);
    }

    @Override
    public ClientGetProfileResponse getProfile(String email) {
        Client client = getClient(email);

        return new ClientGetProfileResponse(
                client.getId().toString(),
                client.getFirstName(),
                client.getLastName(),
                client.getPatronymic(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getBirthday(),
                client.getClientPhotoData() == null ? null : client.getClientPhotoData().getPhoto(),
                client.getClientStatus().getName()
        );
    }

    @Override
    public Client checkClientNotActivated(String email) {
        Client client = getClient(email);

        if (client.getClientStatus().getName().equals(EClientStatus.NOT_ACTIVATED.getName())) {
            throw new ClientNotActivatedException();
        }

        return client;
    }

    private Client getClient(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(InvalidEmailException::new);
    }

    private void saveClientPhoto(String clientPhoto, Client client) {
        client.setClientPhotoData(ClientPhotoData.of(
                clientPhoto,
                client
        ));
    }
}
