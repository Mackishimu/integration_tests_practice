package com.epam.probation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CountryNotFoundException extends ResponseStatusException {

    public CountryNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "No such country name");
    }
}
