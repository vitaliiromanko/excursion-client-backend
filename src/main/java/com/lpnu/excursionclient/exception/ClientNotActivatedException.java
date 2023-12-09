package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ClientNotActivatedException extends ResponseStatusException {
    public ClientNotActivatedException() {
        super(HttpStatus.FORBIDDEN, "Client is not activated");
        log.warn("Client is not activated");
    }
}
