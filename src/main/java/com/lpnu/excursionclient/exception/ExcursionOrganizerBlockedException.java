package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ExcursionOrganizerBlockedException extends ResponseStatusException {
    private final static String MESSAGE = "Excursion organizer is blocked";

    public ExcursionOrganizerBlockedException() {
        super(HttpStatus.FORBIDDEN, MESSAGE);
        log.warn(MESSAGE);
    }
}
