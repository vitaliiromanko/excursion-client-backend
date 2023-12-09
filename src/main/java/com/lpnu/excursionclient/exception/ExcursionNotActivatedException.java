package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ExcursionNotActivatedException extends ResponseStatusException {
    private final static String MESSAGE = "Excursion is not activated";

    public ExcursionNotActivatedException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, MESSAGE);
        log.warn(MESSAGE);
    }
}
