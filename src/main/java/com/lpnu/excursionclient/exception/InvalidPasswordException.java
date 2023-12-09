package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidPasswordException extends ResponseStatusException {
    public InvalidPasswordException() {
        super(HttpStatus.BAD_REQUEST, "Password is invalid");
        log.warn("Password is invalid");
    }
}
