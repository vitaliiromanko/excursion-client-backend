package com.lpnu.excursionclient.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ActivateRequest(
        @NotBlank
        @JsonProperty("activation_token")
        String activationToken
) {
}
