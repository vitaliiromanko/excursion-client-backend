package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record StartDateRange(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonProperty("start")
        LocalDateTime start,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonProperty("end")
        LocalDateTime end
) {
}
