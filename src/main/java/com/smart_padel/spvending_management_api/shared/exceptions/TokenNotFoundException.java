package com.smart_padel.spvending_management_api.shared.exceptions;
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
