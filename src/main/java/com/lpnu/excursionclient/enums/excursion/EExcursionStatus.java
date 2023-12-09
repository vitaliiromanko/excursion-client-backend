package com.lpnu.excursionclient.enums.excursion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EExcursionStatus {
    ACTIVE("АКТИВНА"),
    REJECTED("ВІДХИЛЕНА"),
    BLOCKED("ЗАБЛОКОВАНА"),
    COMPLETED("ЗАВЕРШЕНА"),
    NOT_ACTIVATED("НЕ АКТИВОВАНИЙ"),
    BEING_CHECKED("ПЕРЕВІРЯЄТЬСЯ");

    private final String name;
}
