package com.SmartPadel.spvendingManagerApi.club.application.usecases;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.CreateClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateClubUseCaseImpl implements CreateClubUseCase {

    ClubRepositoryPort clubRepositoryPort;

    @Override
    public Club createClub(String tenantId, Club club) {return clubRepositoryPort.save(tenantId, club);}
}
