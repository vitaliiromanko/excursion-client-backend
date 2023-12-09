package com.lpnu.excursionclient.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        @JsonProperty("first_name")
        String firstName,
        @NotBlank
        @JsonProperty("last_name")
        String lastName,
        @Email
        @JsonProperty("email")
        String email,
        @NotBlank
        @JsonProperty("password")
        String password
) {
}
