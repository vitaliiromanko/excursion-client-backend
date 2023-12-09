package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidClientEmailOrPasswordException extends ResponseStatusException {
    private final static String MESSAGE = "Client email or password is invalid";

    public InvalidClientEmailOrPasswordException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
