package com.SmartPadel.spvendingManagerApi.club.domain.ports.out;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ClubRepositoryPort {
    Page<Club> findAll(String search,Pageable pageable);
    List<Club> findAllClubsSummary();
    Club save(UUID tenantId , Club club);
    Club findById(UUID clubId);
    Club update(UUID tenantId,UUID clubId,Club club);
    void deleteById(UUID clubId);
    Page<Club> findAllClubsByTenantId(String search, UUID tenantId, Pageable pageable);
}
