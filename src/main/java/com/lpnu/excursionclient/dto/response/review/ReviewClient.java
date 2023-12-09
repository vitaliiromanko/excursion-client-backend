package com.lpnu.excursionclient.dto.response.review;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReviewClient(
        @JsonProperty("id")
        String id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("photo")
        String photo
) {
}
