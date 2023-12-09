package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidReviewStatusNameException extends ResponseStatusException {
    public InvalidReviewStatusNameException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Review status name is invalid");
        log.error("Review status name is invalid");
    }
}
