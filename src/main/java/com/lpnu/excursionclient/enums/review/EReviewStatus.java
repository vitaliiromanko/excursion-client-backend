package com.lpnu.excursionclient.enums.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EReviewStatus {
    ACTIVE("АКТИВНИЙ"),
    BLOCKED("ЗАБЛОКОВАНИЙ");

    private final String name;
}
