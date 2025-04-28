package com.SmartPadel.spvendingManagerApi.shared.Exceptions;
public class ParamRequiredException extends RuntimeException {
    public ParamRequiredException(String message) {
        super(message);
    }
}
