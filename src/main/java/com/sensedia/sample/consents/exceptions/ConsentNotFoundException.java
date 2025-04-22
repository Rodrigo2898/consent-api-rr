package com.sensedia.sample.consents.exceptions;

public class ConsentNotFoundException extends RuntimeException {
    public ConsentNotFoundException(String msg) {
        super(msg);
    }
}
