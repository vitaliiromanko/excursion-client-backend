package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DurationResponseItem(
        @JsonProperty("start")
        Double start,
        @JsonProperty("end")
        Double end
) {
}
