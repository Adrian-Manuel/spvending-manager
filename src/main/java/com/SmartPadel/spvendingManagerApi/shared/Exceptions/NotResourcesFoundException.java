package com.SmartPadel.spvendingManagerApi.shared.Exceptions;

public class NotResourcesFoundException extends RuntimeException {
    public NotResourcesFoundException(String message){
        super(message);
    }
}
