package com.smart_padel.spvending_management_api.club.domain.ports.in;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
public interface RetrieveClubUseCase {
    Club getClubById(UUID clubId);
    Page<Club> getAllClubs(String search, Pageable pageable);
    Page<Club> getAllClubsByTenantId(String search,UUID tenantId, Pageable pageable);
    List<Club> getAllClubsSummary();
}
