package com.smart_padel.spvending_management_api.security.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Validated
public class AuthRequest{

    @NotNull(message = "the username is required")
    @Size(min = 3, max = 20, message = "the username must be between 3 and 20 characters")
    @NotBlank(message = "the username is cannot be blank")
    String username;
    @Size(min = 8, message = "the password must be min 8 characters")
    @NotBlank(message = "the password is cannot be blank")
    @NotNull(message = "the password is required")
    String password;
}
