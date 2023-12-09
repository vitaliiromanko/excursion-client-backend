package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class OrderAlreadyCompletedException extends ResponseStatusException {
    public OrderAlreadyCompletedException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Order has already been completed");
        log.warn("Order has already been completed");
    }
}
