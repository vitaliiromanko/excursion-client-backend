package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidClientIdException extends ResponseStatusException {
    public InvalidClientIdException() {
        super(HttpStatus.BAD_REQUEST, "Client id is invalid");
        log.warn("Client id is invalid");
    }
}
