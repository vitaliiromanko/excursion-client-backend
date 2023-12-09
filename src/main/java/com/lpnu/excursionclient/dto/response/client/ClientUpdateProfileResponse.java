package com.lpnu.excursionclient.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientUpdateProfileResponse(
        @JsonProperty("client_status")
        String clientStatus
) {
}
