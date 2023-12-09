package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DurationRequestRange(
        @JsonProperty("start")
        DurationRequestItem start,
        @JsonProperty("end")
        DurationRequestItem end
) {
}
