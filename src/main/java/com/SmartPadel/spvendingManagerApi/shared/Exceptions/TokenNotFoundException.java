package com.SmartPadel.spvendingManagerApi.shared.Exceptions;
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
