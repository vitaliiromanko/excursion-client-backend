package com.lpnu.excursionclient.dto.response.excursion;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

public record GetSearchExcursionListResponse(
        @JsonProperty("excursions")
        Page<GetExcursionItemResponse> excursions
) {
}
