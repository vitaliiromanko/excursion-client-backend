package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidExcursionItemStatusNameException extends ResponseStatusException {
    public InvalidExcursionItemStatusNameException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "ExcursionItem status name is invalid");
        log.error("ExcursionItem status name is invalid");
    }
}
