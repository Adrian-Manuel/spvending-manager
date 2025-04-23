package com.SmartPadel.spvendingManagerApi.security.auth.controller;

public record RegisterRequest (
        String name,
        String password
) {
}
