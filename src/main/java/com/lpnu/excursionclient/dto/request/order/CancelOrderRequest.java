package com.lpnu.excursionclient.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CancelOrderRequest(
        @NotBlank
        @JsonProperty("order_id")
        String orderId,
        @JsonProperty("reason")
        String reason
) {
}
