package com.smart_padel.spvending_management_api.club.application.usecases;
import com.smart_padel.spvending_management_api.club.domain.ports.in.DeleteClubUseCase;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DeleteClubUseCaseImpl implements DeleteClubUseCase {
   private final ClubRepositoryPort clubRepositoryPort;
    @Override
    public void deleteClub(UUID clubId) {clubRepositoryPort.deleteById(clubId);}
}
