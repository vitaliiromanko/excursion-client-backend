package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidClientStatusNameException extends ResponseStatusException {
    public InvalidClientStatusNameException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Client status name is invalid");
        log.error("Client status name is invalid");
    }
}
