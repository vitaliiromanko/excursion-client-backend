package com.lpnu.excursionclient.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelOrderResponse(
        @JsonProperty("order_id")
        String orderId,
        @JsonProperty("order_status")
        String orderStatus
) {
}
