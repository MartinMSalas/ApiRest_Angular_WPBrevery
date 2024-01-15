package com.wpbrewery.mms.walterpenk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErros(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(e.getBindingResult().getFieldError());
    }
}
