package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidOrderStatusNameException extends ResponseStatusException {
    public InvalidOrderStatusNameException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Order status name is invalid");
        log.error("Order status name is invalid");
    }
}
