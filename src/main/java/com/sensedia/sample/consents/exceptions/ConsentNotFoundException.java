package com.sensedia.sample.consents.exceptions;

public class ConsentNotFoundException extends RuntimeException {
    public ConsentNotFoundException(String id) {
        super("Consentimento com ID " + id + " não encontrado");
    }
}
