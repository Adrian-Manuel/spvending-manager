package com.SmartPadel.spvendingManagerApi.club.application.usecases;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.CreateClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateClubUseCaseImpl implements CreateClubUseCase {

    private final ClubRepositoryPort clubRepositoryPort;

    @Override
    public Club createClub(UUID tenantId, Club club) {return clubRepositoryPort.save(tenantId, club);}
}
