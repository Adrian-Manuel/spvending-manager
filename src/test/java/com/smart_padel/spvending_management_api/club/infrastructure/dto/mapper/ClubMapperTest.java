package com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
class ClubMapperTest {
    @Test
    void constructor_ThrowsException() {
        assertThatThrownBy(ClubMapper::new)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Util class");
    }
}
