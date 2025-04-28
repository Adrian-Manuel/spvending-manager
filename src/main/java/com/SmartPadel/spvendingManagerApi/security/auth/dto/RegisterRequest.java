package com.SmartPadel.spvendingManagerApi.security.auth.dto;
import com.SmartPadel.spvendingManagerApi.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}
