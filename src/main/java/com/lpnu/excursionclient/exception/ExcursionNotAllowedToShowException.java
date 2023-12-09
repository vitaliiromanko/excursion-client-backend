package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ExcursionNotAllowedToShowException extends ResponseStatusException {
    private final static String MESSAGE = "Excursion is not allowed to show";

    public ExcursionNotAllowedToShowException() {
        super(HttpStatus.FORBIDDEN, MESSAGE);
        log.warn(MESSAGE);
    }
}
