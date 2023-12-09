package com.lpnu.excursionclient.controller;

import com.lpnu.excursionclient.dto.request.client.ClientUpdateProfilePasswordRequest;
import com.lpnu.excursionclient.dto.request.client.ClientUpdateProfileRequest;
import com.lpnu.excursionclient.dto.response.client.ClientGetProfileResponse;
import com.lpnu.excursionclient.dto.response.client.ClientUpdateProfilePasswordResponse;
import com.lpnu.excursionclient.dto.response.client.ClientUpdateProfileResponse;
import com.lpnu.excursionclient.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ClientController {
    private final ClientService clientService;

    @PutMapping("/update-data")
    public ResponseEntity<ClientUpdateProfileResponse> updateProfile(
            @Valid @RequestBody ClientUpdateProfileRequest clientUpdateProfileRequest,
            Authentication authentication,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok().body(new ClientUpdateProfileResponse(
                clientService.updateProfile((String) authentication.getPrincipal(),
                        clientUpdateProfileRequest, request.getHeader("Origin"))));
    }

    @PutMapping("/update-password")
    public ResponseEntity<ClientUpdateProfilePasswordResponse> updateProfilePassword(
            @Valid @RequestBody ClientUpdateProfilePasswordRequest clientUpdateProfilePasswordRequest,
            Authentication authentication
    ) {
        clientService.updateProfilePassword((String) authentication.getPrincipal(), clientUpdateProfilePasswordRequest);
        return ResponseEntity.ok().body(new ClientUpdateProfilePasswordResponse("success"));
    }

    @GetMapping
    public ResponseEntity<ClientGetProfileResponse> getProfile(
            Authentication authentication
    ) {
        return ResponseEntity.ok().body(clientService.getProfile((String) authentication.getPrincipal()));
    }
}
