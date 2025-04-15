package com.SmartPadel.spvendingManagerApi.club.domain.ports.in;

import java.util.UUID;

public interface DeleteClubUseCase {
    void deleteClub(UUID clubId);
}
