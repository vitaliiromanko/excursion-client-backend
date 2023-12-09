package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class OrderNotBelongClientException extends ResponseStatusException {
    public OrderNotBelongClientException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Order does not belong the client");
        log.warn("Order does not belong the client");
    }
}
