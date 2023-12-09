package com.lpnu.excursionclient.dto.response.site;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GetSiteCarouselExcursionListResponse(
        @JsonProperty("excursions")
        List<GetSiteCarouselExcursionItemResponse> excursions
) {
}
