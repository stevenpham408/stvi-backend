package com.stvi.urlshortener.controller.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(){
        super("Username is already taken.");
    }
}
