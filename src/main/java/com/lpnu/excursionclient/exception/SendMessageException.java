package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class SendMessageException extends ResponseStatusException {
    private final static String MESSAGE = "Error occurred while sending the message";

    public SendMessageException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE);
        log.warn(MESSAGE);
    }
}
