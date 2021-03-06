package com.stvi.urlshortener.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UrlNotValidAdvice {
    @ResponseBody
    @ExceptionHandler(UrlNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String urlNotValidHandler(UrlNotValidException ex){
        return ex.getMessage();
    }
}
