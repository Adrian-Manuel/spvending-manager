package com.SmartPadel.spvendingManagerApi.security.auth.controller;

public record AuthRequest(
        String username,
        String password
) {
}