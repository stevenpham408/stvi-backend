package com.stvi.urlshortener.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotAuthorizedAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String userNotAuthorizedHandler(UserNotAuthorizedException ex){
        return ex.getMessage();
    }
}
