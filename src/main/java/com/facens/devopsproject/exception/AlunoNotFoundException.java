package com.facens.devopsproject.exception;

import com.facens.devopsproject.config.Generated;

public class AlunoNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 1;

    @Generated
    public AlunoNotFoundException(String message) {
        super(message);
    }
}
