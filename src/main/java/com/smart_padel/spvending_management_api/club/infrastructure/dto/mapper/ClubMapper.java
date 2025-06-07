package com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoIn;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutDetail;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutPreview;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutSummary;
public class ClubMapper {
    ClubMapper() {
        throw new IllegalStateException("Util class");
    }
    public static ClubDtoOutPreview toDtoPreview(Club club) {
        return ClubDtoOutPreview.builder()
                .clubId( club.getClubId() )
                .name( club.getName() )
                .address( club.getAddress() )
                .phone( club.getPhone() )
                .tenantEntityName(club.getTenantName())
                .machinesCount(club.getMachinesCount())
                .build();
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
            .tenantEntityName(club.getTenantName())
            .micronId(club.getMicronId())
            .tenantId(club.getTenantId())
            .userManagers(club.getManagers())
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
    public static ClubDtoOutSummary toDtoSummary(Club club){
        return ClubDtoOutSummary.builder()
                .name(club.getName())
                .clubId(club.getClubId())
                .build();
    }
}
