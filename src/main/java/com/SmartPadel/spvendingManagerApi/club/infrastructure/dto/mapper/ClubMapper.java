package com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.mapper;
import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoIn;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOut;
import com.SmartPadel.spvendingManagerApi.externalUser.model.UserManager;
import com.SmartPadel.spvendingManagerApi.machines.modelsV1.Machine;
import java.util.ArrayList;
import java.util.List;


public class ClubMapper {
    public static ClubDtoOut toModel(Club club) {
        if ( club == null ) {
            return null;
        }

        ClubDtoOut.ClubDtoOutBuilder clubDtoOut = ClubDtoOut.builder();

        clubDtoOut.clubId( club.getClubId() );
        clubDtoOut.name( club.getName() );
        clubDtoOut.cif( club.getCif() );
        clubDtoOut.address( club.getAddress() );
        clubDtoOut.phone( club.getPhone() );
        clubDtoOut.email( club.getEmail() );
        clubDtoOut.remark( club.getRemark() );
        clubDtoOut.accountingId( club.getAccountingId() );
        clubDtoOut.tenantEntity( club.getTenantEntity() );
        List<Machine> list = club.getMachines();
        if ( list != null ) {
            clubDtoOut.machines( new ArrayList<Machine>( list ) );
        }
        List<UserManager> list1 = club.getUserManagers();
        if ( list1 != null ) {
            clubDtoOut.userManagers( new ArrayList<UserManager>( list1 ) );
        }

        return clubDtoOut.build();
    }


    public static Club toDto(ClubDtoIn club) {
        if ( club == null ) {
            return null;
        }

        Club.ClubBuilder club1 = Club.builder();

        club1.name( club.getName() );
        club1.cif( club.getCif() );
        club1.address( club.getAddress() );
        club1.phone( club.getPhone() );
        club1.email( club.getEmail() );
        club1.remark( club.getRemark() );
        club1.accountingId( club.getAccountingId() );

        return club1.build();
    }
}
