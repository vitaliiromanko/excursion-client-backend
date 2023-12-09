package com.lpnu.excursionclient.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank
        @JsonProperty("reset_password_token")
        String resetPasswordToken,
        @NotBlank
        @JsonProperty("new_password")
        String newPassword
) {
}
