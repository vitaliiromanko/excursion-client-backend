package com.lpnu.excursionclient.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record OrderExcursion(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("photo")
        String photo,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonProperty("start_date")
        LocalDateTime startDate
) {
}
