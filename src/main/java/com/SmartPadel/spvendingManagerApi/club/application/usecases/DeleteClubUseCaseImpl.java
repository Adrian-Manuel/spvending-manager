package com.SmartPadel.spvendingManagerApi.club.application.usecases;

import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.DeleteClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteClubUseCaseImpl implements DeleteClubUseCase {

    ClubRepositoryPort clubRepositoryPort;

    @Override
    public void deleteClub(UUID clubId) {clubRepositoryPort.deleteById(clubId);}
}
