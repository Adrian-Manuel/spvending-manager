package com.smart_padel.spvending_management_api.club.domain.ports.in;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import java.util.UUID;
public interface CreateClubUseCase { Club createClub(UUID tenantId, Club club);}
