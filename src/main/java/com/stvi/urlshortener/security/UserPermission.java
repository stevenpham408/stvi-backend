package com.stvi.urlshortener.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserPermission {
    // Users are able to READ their own short URLs
    URL_READ("urL:read"),

    // Users are able to create their own short URLs
    URL_WRITE("url:write");

    private final String permission;

    public String getPermission() {
        return permission;
    }
}
