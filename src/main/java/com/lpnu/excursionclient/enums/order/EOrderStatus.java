package com.lpnu.excursionclient.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EOrderStatus {
    DONE("ВИКОНАНО"),
    PROCESSING("ОПРАЦЬОВУЄТЬСЯ"),
    CONFIRMED("ПІДТВЕРДЖЕНО"),
    CANCELED_BY_CLIENT("СКАСОВАНО КЛІЄНТОМ"),
    CANCELED_BY_ORGANIZER("СКАСОВАНО ОРГАНІЗАТОРОМ");

    private final String name;
}
