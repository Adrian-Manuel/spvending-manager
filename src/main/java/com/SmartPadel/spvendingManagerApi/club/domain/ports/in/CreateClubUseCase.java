package com.SmartPadel.spvendingManagerApi.club.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;

public interface CreateClubUseCase {
    Club createClub(String tenantId,Club club);
}
