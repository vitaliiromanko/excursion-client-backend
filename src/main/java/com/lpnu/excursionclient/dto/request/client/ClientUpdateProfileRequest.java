package com.lpnu.excursionclient.dto.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ClientUpdateProfileRequest(
        @NotBlank
        @JsonProperty("first_name")
        String firstName,
        @NotBlank
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("patronymic")
        String patronymic,
        @Email
        @JsonProperty("email")
        String email,
        @Pattern(regexp = "^(?:[+]?\\d{3}\\d{3}\\d{4,6}|)$")
        @JsonProperty("phone_number")
        String phoneNumber,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonProperty("birthday")
        LocalDate birthday,
        @JsonProperty("client_photo")
        String clientPhoto
) {
}
