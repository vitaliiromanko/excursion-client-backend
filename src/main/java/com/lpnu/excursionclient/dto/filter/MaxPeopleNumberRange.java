package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MaxPeopleNumberRange(
        @JsonProperty("start")
        Integer start,
        @JsonProperty("end")
        Integer end
) {
}
