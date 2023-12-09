package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ClientBlockedException extends ResponseStatusException {
    public ClientBlockedException() {
        super(HttpStatus.FORBIDDEN, "Client is blocked");
        log.warn("Client is banned");
    }
}
