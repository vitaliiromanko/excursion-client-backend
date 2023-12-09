package com.lpnu.excursionclient.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
