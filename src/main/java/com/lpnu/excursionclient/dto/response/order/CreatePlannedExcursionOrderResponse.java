package com.lpnu.excursionclient.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePlannedExcursionOrderResponse(
        @JsonProperty("message")
        String message
) {
}
