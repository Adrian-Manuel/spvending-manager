package com.smart_padel.spvending_management_api.club.infrastructure.utils;

import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.repository.JpaClubRepository;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClubHelperAdapterTest {

    @Test
    void testGetClubOrThrow_Exists() {
        UUID clubId = UUID.randomUUID();
        ClubEntity clubEntity = new ClubEntity();

        JpaClubRepository repo = mock(JpaClubRepository.class);
        when(repo.findById(clubId)).thenReturn(Optional.of(clubEntity));

        ClubEntity result = ClubHelperAdapter.getClubOrThrow(repo, clubId);

        assertNotNull(result);
        assertEquals(clubEntity, result);
    }

    @Test
    void testGetClubOrThrow_NotFound() {
        UUID clubId = UUID.randomUUID();
        JpaClubRepository repo = mock(JpaClubRepository.class);
        when(repo.findById(clubId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ClubHelperAdapter.getClubOrThrow(repo, clubId));
    }

    @Test
    void testValidateClubExists_Exists() {
        UUID clubId = UUID.randomUUID();
        JpaClubRepository repo = mock(JpaClubRepository.class);
        when(repo.existsById(clubId)).thenReturn(true);

        assertDoesNotThrow(() -> ClubHelperAdapter.validateClubExists(repo, clubId));
    }

    @Test
    void testValidateClubExists_NotFound() {
        UUID clubId = UUID.randomUUID();
        JpaClubRepository repo = mock(JpaClubRepository.class);
        when(repo.existsById(clubId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> ClubHelperAdapter.validateClubExists(repo, clubId));
    }

    @Test
    void testPrivateConstructorThrowsException() {
        assertThrows(IllegalStateException.class, ClubHelperAdapter::new);
    }
}
