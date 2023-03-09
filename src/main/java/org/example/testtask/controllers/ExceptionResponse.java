package org.example.testtask.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse extends Throwable {
    private String message;
    private String code;
    private String rejectValue;
}
