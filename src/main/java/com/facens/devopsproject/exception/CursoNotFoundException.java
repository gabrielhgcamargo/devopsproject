package com.facens.devopsproject.exception;

import com.facens.devopsproject.config.Generated;

public class CursoNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    @Generated
    public CursoNotFoundException(String message) {
        super(message);
    }
}