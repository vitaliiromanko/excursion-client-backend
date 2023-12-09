package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryItem(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name
) {
}
