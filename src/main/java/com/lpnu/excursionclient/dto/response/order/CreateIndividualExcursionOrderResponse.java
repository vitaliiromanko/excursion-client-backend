package com.lpnu.excursionclient.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateIndividualExcursionOrderResponse(
        @JsonProperty("message")
        String message
) {
}
