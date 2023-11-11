package com.facens.devopsproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.facens.devopsproject.config.Generated;
import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {

    @Generated
    @ExceptionHandler(CursoNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCursoNotFoundException(CursoNotFoundException ex, WebRequest request) {

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @Generated
    @ExceptionHandler(AlunoNotFoundException.class)
    public ResponseEntity<ErrorObject> handleAlunoNotFoundException(AlunoNotFoundException ex, WebRequest request) {

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }
}