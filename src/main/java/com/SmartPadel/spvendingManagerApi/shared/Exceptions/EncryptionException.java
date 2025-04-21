package com.SmartPadel.spvendingManagerApi.shared.Exceptions;

public class EncryptionException extends RuntimeException{
    public EncryptionException(String message, Throwable cause){
        super(message, cause);
    }
}
