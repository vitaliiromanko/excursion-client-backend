package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidOrderIdException extends ResponseStatusException {
    public InvalidOrderIdException() {
        super(HttpStatus.BAD_REQUEST, "Order id is invalid");
        log.warn("Order id is invalid");
    }
}
