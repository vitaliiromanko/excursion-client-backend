package com.lpnu.excursionclient.dto.response.excursion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpnu.excursionclient.dto.filter.*;

import java.util.List;

public record GetFilterResponse(
        @JsonProperty("categories")
        List<CategoryItem> categories,
        @JsonProperty("price_range")
        PriceRange priceRange,
        @JsonProperty("duration_range")
        DurationResponseRange durationResponseRange,
        @JsonProperty("movement_types")
        List<MovementTypeItem> movementTypes,
        @JsonProperty("topic_types")
        List<TopicTypeItem> topicTypes,
        @JsonProperty("conducting_types")
        List<ConductingTypeItem> conductingTypes,
        @JsonProperty("max_people_number_range")
        MaxPeopleNumberRange maxPeopleNumberRange,
        @JsonProperty("start_date_range")
        StartDateRange startDateRange,
        @JsonProperty("organizers")
        List<OrganizerItem> organizers
) {
}
