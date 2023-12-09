package com.lpnu.excursionclient.dto.response.site;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record GetSiteCarouselExcursionItemResponse(
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
        String excursionPhoto
) {
}
