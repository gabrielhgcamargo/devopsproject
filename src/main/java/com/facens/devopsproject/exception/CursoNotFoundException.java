package com.facens.devopsproject.exception;

public class CursoNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public CursoNotFoundException(String message) {
        super(message);
    }
}