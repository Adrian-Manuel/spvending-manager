package com.smart_padel.spvending_management_api.club.application.usecases;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteClubUseCaseImplTest {
    @Mock
    private ClubRepositoryPort clubRepositoryPort;

    @InjectMocks
    private DeleteClubUseCaseImpl deleteClubUseCase;

    @Test
    void deleteClub_ValidClubId_CallsRepositoryDeleteByIdOnce() {
        // Arrange
        UUID clubIdToDelete = UUID.randomUUID();

        // Act
        deleteClubUseCase.deleteClub(clubIdToDelete);

        // Assert
        verify(clubRepositoryPort, times(1)).deleteById(clubIdToDelete);
    }
}
