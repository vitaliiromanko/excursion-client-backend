package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DurationRequestItem(
        @JsonProperty("duration")
        Double duration,
        @JsonProperty("unit")
        String unit
) {
}
