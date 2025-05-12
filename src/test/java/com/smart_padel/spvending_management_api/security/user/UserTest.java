package com.smart_padel.spvending_management_api.security.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {
    @Test
    void isAccountNonExpired_ReturnsTrue() {
        User user = User.builder().build();
        assertThat(user.isAccountNonExpired()).isTrue();
    }

    @Test
    void isAccountNonLocked_ReturnsTrue() {
        User user = User.builder().build();
        assertThat(user.isAccountNonLocked()).isTrue();
    }

    @Test
    void isCredentialsNonExpired_ReturnsTrue() {
        User user = User.builder().build();
        assertThat(user.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void isEnabled_ReturnsTrue() {
        User user = User.builder().build();
        assertThat(user.isEnabled()).isTrue();
    }
}
