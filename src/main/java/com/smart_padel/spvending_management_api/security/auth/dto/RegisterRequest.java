package com.smart_padel.spvending_management_api.security.auth.dto;
import com.smart_padel.spvending_management_api.security.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO for user registration requests containing username, password, and role")
public class RegisterRequest {

    @NotNull(message = "the username is required")
    @Size(min = 3, max = 20, message = "the username must be between 3 and 20 characters")
    @NotBlank(message = "the username is cannot be blank")
    @Schema(description = "Username for registration", example = "padel_user", minLength = 3, maxLength = 20)
    private String username;

    @Size(min = 8, message = "the password must be min 8 characters")
    @NotBlank(message = "the password is cannot be blank")
    @NotNull(message = "the password is required")
    @Schema(description = "Password for registration", example = "securePassword123", minLength = 8)
    private String password;

    @NotNull(message = "the role is required")
    @Schema(description = "Role of the user", example = "ADMIN")
    private Role role;
}
