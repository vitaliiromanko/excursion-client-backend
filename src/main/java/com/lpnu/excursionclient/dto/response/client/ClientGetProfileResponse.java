package com.lpnu.excursionclient.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ClientGetProfileResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("patronymic")
        String patronymic,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone_number")
        String phoneNumber,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonProperty("birthday")
        LocalDate birthday,
        @JsonProperty("client_photo")
        String clientPhoto,
        @JsonProperty("client_status")
        String clientStatus
) {
}
