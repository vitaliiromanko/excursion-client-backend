package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidEmailException extends ResponseStatusException {
    public InvalidEmailException() {
        super(HttpStatus.BAD_REQUEST, "Email is invalid");
        log.warn("Email is invalid");
    }
}
