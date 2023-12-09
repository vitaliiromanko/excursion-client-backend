package com.lpnu.excursionclient.dto.response.excursion.details;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExcursionCategory(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name
) {
}
