package org.example.testtask.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleException(final ResourceNotFoundException exception) {

        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(exception.getMessage());
        error.setCode(exception.getCode());
        error.setRejectValue(exception.getRejectValue());

        return error;
    }
}