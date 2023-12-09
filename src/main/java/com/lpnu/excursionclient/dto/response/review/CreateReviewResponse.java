package com.lpnu.excursionclient.dto.response.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

public record CreateReviewResponse(
        @JsonProperty("reviews")
        Page<ExcursionReview> reviews,
        @JsonProperty("excursion_review_count")
        Integer excursionReviewCount,
        @JsonProperty("excursion_rating")
        Double excursionRating
) {
}
