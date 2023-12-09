package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovementTypeItem(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name
) {
}
