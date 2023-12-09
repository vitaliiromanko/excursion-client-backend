package com.lpnu.excursionclient.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetAllOrderItemResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("excursion")
        OrderExcursion excursion,
        @JsonProperty("status")
        String status,
        @JsonProperty("people_number")
        Integer peopleNumber,
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("excursion_blocked")
        Boolean excursionBlocked,
        @JsonProperty("organizer_blocked")
        Boolean organizerBlocked,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonProperty("creation_date")
        LocalDateTime creationDate
) {
}
