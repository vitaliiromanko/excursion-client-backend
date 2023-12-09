package com.lpnu.excursionclient.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CreateIndividualExcursionOrderRequest(
        @NotBlank
        @JsonProperty("excursion_id")
        String excursionId,
        @NotNull
        @Min(1)
        @JsonProperty("people_number")
        Integer peopleNumber,
        @JsonProperty("comment")
        String comment,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonProperty("start_date")
        LocalDateTime startDate
) {
}
