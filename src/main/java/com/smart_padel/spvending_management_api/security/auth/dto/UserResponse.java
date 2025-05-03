package com.smart_padel.spvending_management_api.security.auth.dto;

import com.smart_padel.spvending_management_api.security.user.Role;

public record UserResponse(String name, Role role){}
