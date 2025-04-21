package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.mapper;

import com.SmartPadel.spvendingManagerApi.shared.Utils.AESGCMEncryption;
import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoIn;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoOutPreview;

public class UserManagerMapper {

    public static UserManagerDtoOutPreview toDtoPreview (UserManager userManager){
        return UserManagerDtoOutPreview.builder()
                .username(userManager.getUserName())
                .userManagerId(userManager.getUserId())
                .micronId(userManager.getMicronId())
                .micronUser(userManager.getMicronUser())
                .tenantEntityName(userManager.getTenantEntity() != null ? userManager.getTenantEntity().getName() : null)
                .clubEntityName(userManager.getClub() != null ? userManager.getClub().getName() : null)
                .build();
    }

    public static UserManagerDtoOutDetail toDtoDetail(UserManager userManager) throws Exception {
        return UserManagerDtoOutDetail.builder()
                .userManagerId(userManager.getUserId())
                .username(userManager.getUserName())
                .password(AESGCMEncryption.decrypt(userManager.getPassword()))
                .micronId(userManager.getMicronId())
                .micronUser(userManager.getMicronUser())
                .micronPass(AESGCMEncryption.decrypt(userManager.getMicronPass()))
                .userType(userManager.getUserType())
                .clubEntityName(userManager.getClub() != null ? userManager.getClub().getName() : null)
                .tenantEntityName(userManager.getTenantEntity() != null ? userManager.getTenantEntity().getName() : null)
                .build();
    }

    public static UserManager toModel(UserManagerDtoIn userManagerDtoIn) throws Exception {
        return UserManager.builder()
                .userName(userManagerDtoIn.getUsername())
                .password(AESGCMEncryption.encrypt(userManagerDtoIn.getPassword()))
                .micronId(userManagerDtoIn.getMicronId())
                .micronUser(userManagerDtoIn.getMicronUser())
                .micronPass(AESGCMEncryption.encrypt(userManagerDtoIn.getMicronPass()))
                .userType(userManagerDtoIn.getUserType())
                .build();
    }


}

