package com.smart_padel.spvending_management_api.club.application.usecases;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveClubUseCaseImplTest {

    @Mock
    private ClubRepositoryPort clubRepositoryPort;

    @InjectMocks
    private RetrieveClubUseCaseImpl retrieveClubUseCase;

    private final UUID clubId = UUID.randomUUID();
    private final UUID tenantId = UUID.randomUUID();
    private final String search = "test";
    private final Pageable pageable = Pageable.ofSize(10);
    private final Club club = Club.builder()
            .clubId(clubId)
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
    private final Page<Club> clubPage = new PageImpl<>(Collections.singletonList(club), pageable, 1);
    private final List<Club> clubList = Collections.singletonList(club);

    @Test
    void getClubById_ValidClubId_CallsRepositoryFindByIdAndReturnsResult() {
        // Arrange
        when(clubRepositoryPort.findById(clubId)).thenReturn(club);

        // Act
        Club result = retrieveClubUseCase.getClubById(clubId);

        // Assert
        assertEquals(club, result);
        verify(clubRepositoryPort, times(1)).findById(clubId);
    }

    @Test
    void getAllClubs_WithSearchAndPageable_CallsRepositoryFindAllAndReturnsResult() {
        // Arrange
        when(clubRepositoryPort.findAll(search, pageable)).thenReturn(clubPage);

        // Act
        Page<Club> result = retrieveClubUseCase.getAllClubs(search, pageable);

        // Assert
        assertEquals(clubPage, result);
        verify(clubRepositoryPort, times(1)).findAll(search, pageable);
    }

    @Test
    void getAllClubs_NullSearchAndPageable_CallsRepositoryFindAllWithNullAndReturnsResult() {
        // Arrange
        when(clubRepositoryPort.findAll(null, pageable)).thenReturn(clubPage);

        // Act
        Page<Club> result = retrieveClubUseCase.getAllClubs(null, pageable);

        // Assert
        assertEquals(clubPage, result);
        verify(clubRepositoryPort, times(1)).findAll(null, pageable);
    }

    @Test
    void getAllClubsByTenantId_WithSearchTenantIdAndPageable_CallsRepositoryFindAllClubsByTenantIdAndReturnsResult() {
        // Arrange
        when(clubRepositoryPort.findAllClubsByTenantId(search, tenantId, pageable)).thenReturn(clubPage);

        // Act
        Page<Club> result = retrieveClubUseCase.getAllClubsByTenantId(search, tenantId, pageable);

        // Assert
        assertEquals(clubPage, result);
        verify(clubRepositoryPort, times(1)).findAllClubsByTenantId(search, tenantId, pageable);
    }


    @Test
    void getAllClubsSummary_CallsRepositoryFindAllClubsSummaryAndReturnsResult() {
        // Arrange
        when(clubRepositoryPort.findAllClubsSummary()).thenReturn(clubList);

        // Act
        List<Club> result = retrieveClubUseCase.getAllClubsSummary();

        // Assert
        assertEquals(clubList, result);
        verify(clubRepositoryPort, times(1)).findAllClubsSummary();
    }
}