package com.lpnu.excursionclient.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientUpdateProfilePasswordResponse(
        @JsonProperty("message")
        String message
) {
}
