package com.lpnu.excursionclient.dto.response.excursion.details;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExcursionOrganizerContactPerson(
        @JsonProperty("id")
        String id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone_numbers")
        List<String> phoneNumbers
) {
}
