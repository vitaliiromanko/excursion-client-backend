package com.lpnu.excursionclient.dto.response.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ExcursionReview(
        @JsonProperty("id")
        String id,
        @JsonProperty("comment")
        String comment,
        @JsonProperty("client")
        ReviewClient client,
        @JsonProperty("rating")
        Integer rating,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonProperty("creation_date")
        LocalDateTime creationDate,
        @JsonProperty("status")
        String status
) {
}
