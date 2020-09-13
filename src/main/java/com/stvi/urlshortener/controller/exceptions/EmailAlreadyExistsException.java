package com.stvi.urlshortener.controller.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(){
        super("Email is already taken.");
    }
}
