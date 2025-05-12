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
class CreateClubUseCaseImplTest {

    @Mock
    private ClubRepositoryPort clubRepositoryPort;

    @InjectMocks
    private CreateClubUseCaseImpl createClubUseCase;

    @Test
    void createClub_ValidInput_CallsRepositoryWithCorrectArgumentsAndReturnsSavedClub() {
        // Arrange
        UUID tenantId = UUID.randomUUID();
        Club clubToCreate = Club.builder()
                .name("Test Club")
                .cif("TESTCIF")
                .address("Test Address")
                .phone("123456789")
                .email("test@example.com")
                .remark("Test Remark")
                .accountingId("ACCOUNTING123")
                .micronId("MICRON456")
                .tenantName("Test Tenant")
                .machinesCount(5)
                .managers(Collections.singletonList("manager1"))
                .build();
        Club savedClub = Club.builder()
                .clubId(UUID.randomUUID())
                .name("Test Club")
                .cif("TESTCIF")
                .address("Test Address")
                .phone("123456789")
                .email("test@example.com")
                .remark("Test Remark")
                .accountingId("ACCOUNTING123")
                .micronId("MICRON456")
                .tenantName("Test Tenant")
                .machinesCount(5)
                .managers(Collections.singletonList("manager1"))
                .build();

        when(clubRepositoryPort.save(tenantId, clubToCreate)).thenReturn(savedClub);

        // Act
        Club result = createClubUseCase.createClub(tenantId, clubToCreate);

        // Assert
        assertNotNull(result);
        assertEquals(savedClub, result);
        verify(clubRepositoryPort, times(1)).save(tenantId, clubToCreate);
    }
}
