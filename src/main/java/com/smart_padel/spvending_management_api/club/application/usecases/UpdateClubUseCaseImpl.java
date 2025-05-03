package com.smart_padel.spvending_management_api.club.application.usecases;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.UpdateClubUseCase;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UpdateClubUseCaseImpl implements UpdateClubUseCase {
    private final ClubRepositoryPort clubRepositoryPort;
    @Override
    public Club updateClub(UUID tenantId,UUID clubId, Club updateClub) {
        return clubRepositoryPort.update(tenantId,clubId, updateClub);
    }
}
