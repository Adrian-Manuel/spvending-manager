package com.smart_padel.spvending_management_api.machine.infrastructure.utils;

import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.entity.MachineEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MachineSpecificationTest {
    @Test
    void withFilter_NullFilter_ReturnsSpecification() {
        Specification<MachineEntity> specification = MachineSpecification.withFilter(null);

        assertThat(specification).isNotNull();
    }

    @Test
    void withFilter_ValidFilter_ReturnsNonNullSpecification() {
        Specification<MachineEntity> specification = MachineSpecification.withFilter("name:Machine1");

        assertThat(specification).isNotNull();
    }

    @Test
    void belongsToClub_ValidClubId_ReturnsSpecification() {
        UUID clubId = UUID.randomUUID();
        Specification<MachineEntity> specification = MachineSpecification.belongsToClub(clubId);

        assertThat(specification).isNotNull();
    }

    @Test
    void belongsToClub_NullClubId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                MachineSpecification.belongsToClub(null));
    }

    @Test
    void privateConstructor_ThrowsIllegalStateException() throws NoSuchMethodException {
        var constructor = MachineSpecification.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        assertThat(exception.getCause()).isInstanceOf(IllegalStateException.class);
    }
}