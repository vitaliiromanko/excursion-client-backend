package com.lpnu.excursionclient.dto.response.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

public record GetExcursionReviewsListResponse(
        @JsonProperty("reviews")
        Page<ExcursionReview> reviews
) {
}
