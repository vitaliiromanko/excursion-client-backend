package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidResetPasswordTokenException extends ResponseStatusException {
    public InvalidResetPasswordTokenException() {
        super(HttpStatus.BAD_REQUEST, "Reset password token is invalid");
        log.warn("Reset password token is invalid");
    }
}
