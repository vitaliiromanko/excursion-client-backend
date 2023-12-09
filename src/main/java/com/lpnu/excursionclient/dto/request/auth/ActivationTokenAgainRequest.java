package com.lpnu.excursionclient.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ActivationTokenAgainRequest(
        @NotBlank
        @JsonProperty("client_id")
        String clientId
) {
}
