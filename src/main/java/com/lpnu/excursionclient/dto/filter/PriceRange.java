package com.lpnu.excursionclient.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PriceRange(
        @JsonProperty("start")
        BigDecimal start,
        @JsonProperty("end")
        BigDecimal end
) {
}
