package com.smart_padel.spvending_management_api.shared.exceptions;
public class EncryptionException extends RuntimeException{
    public EncryptionException(String message, Throwable cause){
        super(message, cause);
    }
}
