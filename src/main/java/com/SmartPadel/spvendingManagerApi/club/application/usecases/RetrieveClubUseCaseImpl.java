package com.SmartPadel.spvendingManagerApi.club.application.usecases;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.RetrieveClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveClubUseCaseImpl implements RetrieveClubUseCase {

    private final ClubRepositoryPort clubRepositoryPort;

    @Override
    public Club getClubById(UUID clubId) {return clubRepositoryPort.findById(clubId);}

    @Override
    public Page<Club> getAllClubs(Pageable pageable) {return clubRepositoryPort.findAll(pageable);}

    @Override
    public Page<Club> getAllClubs(String search, Pageable pageable) {
        return clubRepositoryPort.findAll(search, pageable);
    }

    @Override
    public Page<Club> getAllClubsByTenantId(String search,UUID tenantId, Pageable pageable) {
        return clubRepositoryPort.findAllClubsByTenantId(search,tenantId, pageable);
    }

}
