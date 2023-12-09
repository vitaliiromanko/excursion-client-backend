package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidExcursionPhotosException extends ResponseStatusException {
    public InvalidExcursionPhotosException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Excursion photos cannot be null");
        log.error("Excursion photos cannot be null");
    }
}
