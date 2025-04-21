package com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.mapper;
import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoIn;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;

import java.util.Collections;
import java.util.stream.Collectors;


public class ClubMapper {
    public static ClubDtoOutPreview toDtoPreview(Club club) {
        return ClubDtoOutPreview.builder()
        .clubId( club.getClubId() )
        .name( club.getName() )
        .address( club.getAddress() )
        .phone( club.getPhone() )
        .tenantEntityName(club.getTenantEntity().getName())
        .machinesCount((club.getMachineEntities()!=null) ? club.getMachineEntities().size(): 0).build();
    }


    public static ClubDtoOutDetail toDtoDetail(Club club) {
    return  ClubDtoOutDetail.builder()
            .clubId(club.getClubId())
            .name(club.getName())
            .cif(club.getCif())
            .address( club.getAddress() )
            .phone( club.getPhone() )
            .email( club.getEmail() )
            .remark( club.getRemark() )
            .accountingId( club.getAccountingId() )
            .tenantEntityName(club.getTenantEntity().getName())
            .micronId(club.getMicronId())
            .userManagers((club.getUserManagerEntities() !=null) ? club.getUserManagerEntities()
                    .stream()
                    .map(UserManagerEntity::getUserName)
                    .collect(Collectors.toList())
                    : Collections.emptyList())
            .build();
    }

    public static Club toModel(ClubDtoIn clubDtoIn){
        return Club.builder()
                .name(clubDtoIn.getName())
                .cif(clubDtoIn.getCif())
                .address(clubDtoIn.getAddress())
                .phone(clubDtoIn.getPhone())
                .email(clubDtoIn.getEmail())
                .remark(clubDtoIn.getRemark())
                .micronId(clubDtoIn.getMicronId())
                .accountingId(clubDtoIn.getAccountingId())
                .build();
    }
}
