package com.stvi.urlshortener.controller.exceptions;

public class UrlNotValidException extends RuntimeException {
    public UrlNotValidException(){
        super("Url entered is not valid.");
    }
}
