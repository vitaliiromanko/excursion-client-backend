package com.lpnu.excursionclient.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email
        @JsonProperty("email")
        String email,
        @NotBlank
        @JsonProperty("password")
        String password
) {
}
