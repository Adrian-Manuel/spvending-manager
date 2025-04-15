package com.SmartPadel.spvendingManagerApi.club.application.usecases;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.UpdateClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateClubUseCaseImpl implements UpdateClubUseCase {

    ClubRepositoryPort clubRepositoryPort;

    @Override
    public Club updateClub(UUID clubId, Club updateClub) {
        return clubRepositoryPort.update(clubId, updateClub);
    }
}
