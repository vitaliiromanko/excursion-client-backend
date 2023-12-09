package com.lpnu.excursionclient.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

public record GetAllOrderListResponse(
        @JsonProperty("orders")
        Page<GetAllOrderItemResponse> orders
) {
}
