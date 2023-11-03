package com.facens.devopsproject.exception;

public class AlunoNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 1;

    public AlunoNotFoundException(String message) {
        super(message);
    }
}
