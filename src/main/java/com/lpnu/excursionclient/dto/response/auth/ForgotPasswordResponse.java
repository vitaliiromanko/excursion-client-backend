package com.lpnu.excursionclient.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ForgotPasswordResponse(
        @JsonProperty("message")
        String message
) {
}