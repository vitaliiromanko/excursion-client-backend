package com.lpnu.excursionclient.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePlannedExcursionOrderRequest(
        @NotBlank
        @JsonProperty("excursion_item_id")
        String excursionItemId,
        @NotNull
        @Min(1)
        @JsonProperty("people_number")
        Integer peopleNumber,
        @JsonProperty("comment")
        String comment
) {
}
