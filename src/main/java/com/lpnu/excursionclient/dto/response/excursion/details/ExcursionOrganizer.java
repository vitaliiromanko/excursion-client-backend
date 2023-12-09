package com.lpnu.excursionclient.dto.response.excursion.details;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExcursionOrganizer(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("logo")
        String logo,
        @JsonProperty("contact_people")
        List<ExcursionOrganizerContactPerson> contactPeople
) {
}
