package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidExcursionItemIdException extends ResponseStatusException {
    public InvalidExcursionItemIdException() {
        super(HttpStatus.BAD_REQUEST, "Excursion item id is invalid");
        log.warn("Excursion item id is invalid");
    }
}
