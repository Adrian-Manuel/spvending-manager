package com.smart_padel.spvending_management_api.shared.exceptions;
public class NotResourcesFoundException extends RuntimeException {
    public NotResourcesFoundException(String message){
        super(message);
    }
}
