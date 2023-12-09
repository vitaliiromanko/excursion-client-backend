package com.lpnu.excursionclient.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
