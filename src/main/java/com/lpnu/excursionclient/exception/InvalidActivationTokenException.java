package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidActivationTokenException extends ResponseStatusException {
    public InvalidActivationTokenException() {
        super(HttpStatus.BAD_REQUEST, "Activation token is invalid");
        log.warn("Activation token is invalid");
    }
}
