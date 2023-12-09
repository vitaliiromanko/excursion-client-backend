package com.lpnu.excursionclient.enums.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EClientStatus {
    ACTIVE("АКТИВНИЙ"),
    BLOCKED("ЗАБЛОКОВАНИЙ"),
    NOT_ACTIVATED("НЕ АКТИВОВАНИЙ");

    private final String name;
}
