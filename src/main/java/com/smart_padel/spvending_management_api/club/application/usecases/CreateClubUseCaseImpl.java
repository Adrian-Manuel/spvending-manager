package com.smart_padel.spvending_management_api.club.application.usecases;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.CreateClubUseCase;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
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
