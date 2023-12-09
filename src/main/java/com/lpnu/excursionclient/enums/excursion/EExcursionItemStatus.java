package com.lpnu.excursionclient.enums.excursion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EExcursionItemStatus {
    EXECUTED("ВИКОНАНА"),
    RECRUITMENT_OF_PARTICIPANTS("НАБІР УЧАСНИКІВ"),
    AWAITING_CONFIRMATION("ОЧІКУЄ ПІДТВЕРДЖЕННЯ"),
    CONFIRMED("ПІДТВЕРДЖЕНА"),
    CANCELED_BY_CLIENT("СКАСОВАНА КЛІЄНТОМ"),
    CANCELED_BY_ORGANIZER("СКАСОВАНА ОРГАНІЗАТОРОМ"),
    PARTICIPANTS_RECRUITED("УЧАСНИКІВ НАБРАНО");

    private final String name;
}
