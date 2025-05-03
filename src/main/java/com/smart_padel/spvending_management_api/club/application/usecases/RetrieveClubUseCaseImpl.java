package com.smart_padel.spvending_management_api.club.application.usecases;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.RetrieveClubUseCase;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class RetrieveClubUseCaseImpl implements RetrieveClubUseCase {
    private final ClubRepositoryPort clubRepositoryPort;
    @Override
    public Club getClubById(UUID clubId) {return clubRepositoryPort.findById(clubId);}
    @Override
    public Page<Club> getAllClubs(String search, Pageable pageable) {
        return clubRepositoryPort.findAll(search, pageable);
    }
    @Override
    public Page<Club> getAllClubsByTenantId(String search,UUID tenantId, Pageable pageable) {
        return clubRepositoryPort.findAllClubsByTenantId(search,tenantId, pageable);
    }
    @Override
    public List<Club> getAllClubsSummary() {
        return clubRepositoryPort.findAllClubsSummary();
    }
}
