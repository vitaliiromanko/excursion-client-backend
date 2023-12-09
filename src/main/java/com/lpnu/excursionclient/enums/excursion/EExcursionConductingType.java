package com.lpnu.excursionclient.enums.excursion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EExcursionConductingType {
    PLANNED("запланована"),
    INDIVIDUAL("індивідуальна");

    private final String name;
}
