package com.smart_padel.spvending_management_api.security.auth.dto;

import com.smart_padel.spvending_management_api.security.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(@Schema(description = "The username of the user", example = "padel_user")
                           String name,
                           @Schema(description = "The role assigned to the user", example = "ADMIN")
                           Role role
){}
