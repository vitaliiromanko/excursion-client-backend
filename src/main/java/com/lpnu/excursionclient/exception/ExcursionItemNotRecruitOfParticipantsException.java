package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ExcursionItemNotRecruitOfParticipantsException extends ResponseStatusException {
    private final static String MESSAGE = "Excursion item does not recruit of participants";

    public ExcursionItemNotRecruitOfParticipantsException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, MESSAGE);
        log.warn(MESSAGE);
    }
}
