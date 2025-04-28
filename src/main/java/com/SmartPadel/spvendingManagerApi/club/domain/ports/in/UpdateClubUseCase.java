package com.SmartPadel.spvendingManagerApi.club.domain.ports.in;
import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import java.util.UUID;
public interface UpdateClubUseCase { Club updateClub(UUID tenantId,UUID clubId, Club updateClub);}
