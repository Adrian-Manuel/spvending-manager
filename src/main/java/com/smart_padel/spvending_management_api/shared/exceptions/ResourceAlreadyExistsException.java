package com.smart_padel.spvending_management_api.shared.exceptions;
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
