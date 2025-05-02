package com.SmartPadel.spvendingManagerApi.security.auth.dto;

import com.SmartPadel.spvendingManagerApi.security.user.Role;

public record UserResponse(String name, Role role){}
