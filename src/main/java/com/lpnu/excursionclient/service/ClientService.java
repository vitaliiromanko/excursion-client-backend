package com.lpnu.excursionclient.service;

import com.lpnu.excursionclient.dto.request.client.ClientUpdateProfilePasswordRequest;
import com.lpnu.excursionclient.dto.request.client.ClientUpdateProfileRequest;
import com.lpnu.excursionclient.dto.response.client.ClientGetProfileResponse;
import com.lpnu.excursionclient.model.client.Client;

public interface ClientService {
    String updateProfile(String email, ClientUpdateProfileRequest clientUpdateProfileRequest, String originUrl);

    void updateProfilePassword(String userEmail, ClientUpdateProfilePasswordRequest clientUpdateProfilePasswordRequest);

    ClientGetProfileResponse getProfile(String email);

    Client checkClientNotActivated(String email);
}
