package com.lpnu.excursionclient.dto.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ClientUpdateProfilePasswordRequest(
        @NotBlank
        @JsonProperty("old_password")
        String oldPassword,
        @NotBlank
        @JsonProperty("new_password")
        String newPassword
) {
}
