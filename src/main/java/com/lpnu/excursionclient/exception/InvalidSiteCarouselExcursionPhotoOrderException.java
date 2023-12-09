package com.lpnu.excursionclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidSiteCarouselExcursionPhotoOrderException extends ResponseStatusException {
    public InvalidSiteCarouselExcursionPhotoOrderException() {
        super(HttpStatus.BAD_REQUEST, "Site carousel excursion photo order is invalid");
        log.warn("Site carousel excursion photo order is invalid");
    }
}
