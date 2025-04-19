package com.sensedia.sample.consents.service.exceptions;

public class ConsentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConsentNotFoundException(String msg) {
        super(msg);
    }
}
