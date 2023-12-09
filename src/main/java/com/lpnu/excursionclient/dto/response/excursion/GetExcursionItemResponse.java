package com.lpnu.excursionclient.dto.response.excursion;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record GetExcursionItemResponse(
        @JsonProperty("excursion_id")
        String excursionId,
        @JsonProperty("excursion_name")
        String excursionName,
        @JsonProperty("excursion_description")
        String excursionDescription,
        @JsonProperty("excursion_price")
        BigDecimal excursionPrice,
        @JsonProperty("excursion_discount")
        BigDecimal excursionDiscount,
        @JsonProperty("excursion_photo")
        String excursionPhoto,
        @JsonProperty("excursion_rating")
        Double excursionRating,
        @JsonProperty("excursion_review_number")
        Integer excursionReviewNumber,
        @JsonProperty("excursion_max_people_number")
        Integer excursionMaxPeopleNumber,
        @JsonProperty("excursion_duration")
        Double excursionDuration,
        @JsonProperty("excursion_duration_unit")
        String excursionDurationUnit
) {
}
