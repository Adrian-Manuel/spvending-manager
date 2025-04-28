package com.SmartPadel.spvendingManagerApi.club.domain.ports.in;
import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import java.util.UUID;
public interface CreateClubUseCase { Club createClub(UUID tenantId, Club club);}
