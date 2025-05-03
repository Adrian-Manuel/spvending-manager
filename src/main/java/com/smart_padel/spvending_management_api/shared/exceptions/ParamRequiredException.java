package com.smart_padel.spvending_management_api.shared.exceptions;
public class ParamRequiredException extends RuntimeException {
    public ParamRequiredException(String message) {
        super(message);
    }
}
