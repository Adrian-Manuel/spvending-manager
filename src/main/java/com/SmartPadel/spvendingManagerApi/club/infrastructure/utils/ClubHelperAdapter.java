package com.SmartPadel.spvendingManagerApi.club.infrastructure.utils;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.repository.JpaClubRepository;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;


import java.util.UUID;

public class ClubHelperAdapter {
    public static ClubEntity getClubOrThrow(JpaClubRepository repo, UUID clubId) {
        return repo.findById(clubId).orElseThrow(() -> new ResourceNotFoundException("The requested club was not found"));
    }

    public static void validateClubExists(JpaClubRepository repo, UUID clubId) {
        if (!repo.existsById(clubId)) {
            throw new ResourceNotFoundException("The club does not exist");
        }
    }
}
