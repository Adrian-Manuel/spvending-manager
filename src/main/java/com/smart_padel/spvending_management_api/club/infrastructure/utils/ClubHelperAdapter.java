package com.smart_padel.spvending_management_api.club.infrastructure.utils;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.repository.JpaClubRepository;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import java.util.UUID;
public class ClubHelperAdapter {
    private ClubHelperAdapter() {
        throw new IllegalStateException("Helper class");
    }
    public static ClubEntity getClubOrThrow(JpaClubRepository repo, UUID clubId) {
        return repo.findById(clubId).orElseThrow(() -> new ResourceNotFoundException("The requested club was not found"));
    }
    public static void validateClubExists(JpaClubRepository repo, UUID clubId) {
        if (!repo.existsById(clubId)) {
            throw new ResourceNotFoundException("The club does not exist");
        }
    }
}
