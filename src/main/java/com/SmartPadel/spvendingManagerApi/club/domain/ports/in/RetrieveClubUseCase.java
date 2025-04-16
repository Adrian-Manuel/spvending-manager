package com.SmartPadel.spvendingManagerApi.club.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RetrieveClubUseCase {
    Club getClubById(UUID clubId);
    Page<Club> getAllClubs(Pageable pageable);
    Page<Club> getAllClubs(String search, Pageable pageable);
    Page<Club> getAllClubsByTenantId(String search,UUID tenantId, Pageable pageable);
}
