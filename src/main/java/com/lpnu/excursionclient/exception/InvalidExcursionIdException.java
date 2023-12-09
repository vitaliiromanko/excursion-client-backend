package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidExcursionIdException extends ResponseStatusException {
    public InvalidExcursionIdException() {
        super(HttpStatus.BAD_REQUEST, "Excursion id is invalid");
        log.warn("Excursion id is invalid");
    }
}
