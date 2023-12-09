package com.lpnu.excursionclient.dto.request.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReviewRequest(
        @NotBlank
        @JsonProperty("excursion_id")
        String excursionId,
        @NotNull
        @JsonProperty("rating")
        Integer rating,
        @NotBlank
        @JsonProperty("comment")
        String comment
) {
}
