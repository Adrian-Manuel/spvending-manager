package com.smart_padel.spvending_management_api.club.application.usecases;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateClubUseCaseImplTest {
    @Mock
    private ClubRepositoryPort clubRepositoryPort;

    @InjectMocks
    private UpdateClubUseCaseImpl updateClubUseCase;

    private final UUID tenantId = UUID.randomUUID();
    private final UUID clubId = UUID.randomUUID();
    private final Club updatedClub = Club.builder()
            .clubId(clubId)
            .name("Updated Club")
            .cif("UPDATEDCIF")
            .address("Updated Address")
            .phone("987654321")
            .email("updated@example.com")
            .remark("Updated Remark")
            .accountingId("UPDATEDACCOUNTING")
            .micronId("UPDATEDMICRON")
            .tenantName("Updated Tenant")
            .machinesCount(10)
            .managers(Collections.singletonList("manager2"))
            .build();

    @Test
    void updateClub_ValidInput_CallsRepositoryUpdateAndReturnsUpdatedClub() {
        // Arrange
        when(clubRepositoryPort.update(tenantId, clubId, updatedClub)).thenReturn(updatedClub);

        // Act
        Club result = updateClubUseCase.updateClub(tenantId, clubId, updatedClub);

        // Assert
        assertNotNull(result);
        assertEquals(updatedClub, result);
        verify(clubRepositoryPort, times(1)).update(tenantId, clubId, updatedClub);
    }





}
