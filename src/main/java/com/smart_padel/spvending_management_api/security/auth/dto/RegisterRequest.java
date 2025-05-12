package com.smart_padel.spvending_management_api.security.auth.dto;
import com.smart_padel.spvending_management_api.security.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class RegisterRequest {
    @NotNull(message = "the username is required")
    @Size(min = 3, max = 20, message = "the username must be between 3 and 20 characters")
    @NotBlank(message = "the username is cannot be blank")
    private String username;
    @Size(min = 8, message = "the password must be min 8 characters")
    @NotBlank(message = "the password is cannot be blank")
    @NotNull(message = "the password is required")
    private String password;
    @NotNull(message = "the role is required")
    private Role role;
}
