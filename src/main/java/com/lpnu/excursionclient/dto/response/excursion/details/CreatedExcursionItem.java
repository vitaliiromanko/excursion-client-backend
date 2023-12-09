package com.lpnu.excursionclient.dto.response.excursion.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CreatedExcursionItem(
        @JsonProperty("id")
        String id,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonProperty("start_date")
        LocalDateTime startDate
) {
}
