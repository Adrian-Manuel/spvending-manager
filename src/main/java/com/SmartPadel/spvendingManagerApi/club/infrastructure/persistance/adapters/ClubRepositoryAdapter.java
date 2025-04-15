package com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.adapters;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.out.ClubRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ClubRepositoryAdapter implements ClubRepositoryPort {

    @Override
    public Page<Club> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Club> findAll(String search, Pageable pageable) {
        return null;
    }

    @Override
    public Club save(String tenantId, Club club) {
        return null;
    }

    @Override
    public Club findById(UUID clubId) {
        return null;
    }

    @Override
    public Club update(UUID clubId, Club club) {
        return null;
    }

    @Override
    public void deleteById(UUID clubId) {

    }
}
