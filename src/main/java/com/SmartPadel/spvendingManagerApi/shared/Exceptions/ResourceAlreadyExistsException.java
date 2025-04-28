package com.SmartPadel.spvendingManagerApi.shared.Exceptions;
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
