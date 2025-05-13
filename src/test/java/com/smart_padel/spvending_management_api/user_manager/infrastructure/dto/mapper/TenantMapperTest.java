package com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper.TenantMapper;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
class TenantMapperTest {

    @Test
    void constructor_ThrowsException() {
        assertThatThrownBy(() -> {
            Constructor<TenantMapper> constructor = TenantMapper.class.getDeclaredConstructor();
            constructor.setAccessible(true); // Permitir acceso al constructor privado
            constructor.newInstance();
        }).cause() // Verificar la causa subyacente
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Util class");
    }
}
