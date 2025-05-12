package com.smart_padel.spvending_management_api.user_manager.infrastructure.utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(MockitoExtension.class)
class UserManagerSpecificationTest {

    @Test
    void withFilter_ReturnsNullSpecification_WhenFilterIsNullEmptyOrBlank() {
        String[] filters = {null, "", "   "};
        for (String filter : filters) {
            assertThat(UserManagerSpecification.withFilter(filter)).isNull();
        }
    }

    @Test
    void withFilter_ReturnsSpecification_WhenFilterIsNotBlank() {
        String[] filters = {"name:John", "  name:John  "};
        for (String filter : filters) {
            assertThat(UserManagerSpecification.withFilter(filter)).isNotNull();
        }
    }

    @Test
    void constructor_ThrowsException() {
        assertThatThrownBy(UserManagerSpecification::new)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Util class");
    }
}