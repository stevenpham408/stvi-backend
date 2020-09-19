package com.stvi.urlshortener.controller.exceptions;

public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(){ super("User not authorized."); }
}
