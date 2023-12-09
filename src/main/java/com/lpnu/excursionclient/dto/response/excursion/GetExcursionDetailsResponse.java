package com.lpnu.excursionclient.dto.response.excursion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpnu.excursionclient.dto.response.excursion.details.*;

import java.math.BigDecimal;
import java.util.List;

public record GetExcursionDetailsResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("discount")
        BigDecimal discount,
        @JsonProperty("photos")
        List<String> photos,
        @JsonProperty("status")
        String status,
        @JsonProperty("duration")
        Double duration,
        @JsonProperty("duration_unit")
        String durationUnit,
        @JsonProperty("movement_type")
        MovementType movementType,
        @JsonProperty("topic_types")
        List<TopicType> topicTypes,
        @JsonProperty("conducting_type")
        ConductingType conductingType,
        @JsonProperty("max_people_number")
        Integer maxPeopleNumber,
        @JsonProperty("rating")
        Double rating,
        @JsonProperty("excursion_items")
        List<CreatedExcursionItem> excursionItems,
        @JsonProperty("review_number")
        Integer reviewNumber,
        @JsonProperty("category")
        ExcursionCategory category,
        @JsonProperty("organizer")
        ExcursionOrganizer organizerId
) {
}
