package com.SmartPadel.spvendingManagerApi.club.domain.ports.out;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClubRepositoryPort {
    Page<Club> findAll(Pageable pageable);
    Page<Club> findAll(String search,Pageable pageable);
    Club save(String tenantId ,Club club);
    Club findById(UUID clubId);
    Club update(UUID clubId,Club club);
    void deleteById(UUID clubId);
}
