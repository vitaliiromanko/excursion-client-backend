package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DurationResponseRange(
        @JsonProperty("hour")
        DurationResponseItem hour,
        @JsonProperty("day")
        DurationResponseItem day
) {
}
