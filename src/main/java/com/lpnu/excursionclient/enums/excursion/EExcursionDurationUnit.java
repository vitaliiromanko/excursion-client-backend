package com.lpnu.excursionclient.enums.excursion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EExcursionDurationUnit {
    HOURS("години"),
    DAYS("дні");

    private final String name;
}
