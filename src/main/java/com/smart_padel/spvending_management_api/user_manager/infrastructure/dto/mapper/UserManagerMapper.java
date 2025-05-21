package com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper;
import com.smart_padel.spvending_management_api.shared.utils.AESGCMEncryption;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutDetail;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutPreview;

import java.security.GeneralSecurityException;

public class UserManagerMapper {

    private UserManagerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserManagerDtoOutPreview toDtoPreview (UserManager userManager){
        return UserManagerDtoOutPreview.builder()
                .username(userManager.getUserName())
                .userManagerId(userManager.getUserId())
                .micronId(userManager.getMicronId())
                .micronUser(userManager.getMicronUser())
                .tenantEntityName(userManager.getTenantName())
                .clubEntityName(userManager.getClubName())
                .build();
    }

    public static UserManagerDtoOutDetail toDtoDetail(UserManager userManager, String aeSecretKey) throws GeneralSecurityException {
        return UserManagerDtoOutDetail.builder()
                .userManagerId(userManager.getUserId())
                .username(userManager.getUserName())
                .password(AESGCMEncryption.decrypt(userManager.getPassword(), aeSecretKey))
                .micronId(userManager.getMicronId())
                .micronUser(userManager.getMicronUser())
                .micronPass(AESGCMEncryption.decrypt(userManager.getMicronPass(), aeSecretKey))
                .userType(userManager.getUserType())
                .clubEntityName(userManager.getClubName())
                .tenantEntityName(userManager.getTenantName())
                .build();
    }

    public static UserManager toModel(UserManagerDtoIn userManagerDtoIn, String aeSecretKey) throws GeneralSecurityException {
        return UserManager.builder()
                .userName(userManagerDtoIn.getUsername())
                .password(AESGCMEncryption.encrypt(userManagerDtoIn.getPassword(), aeSecretKey))
                .micronId(userManagerDtoIn.getMicronId())
                .micronUser(userManagerDtoIn.getMicronUser())
                .micronPass(AESGCMEncryption.encrypt(userManagerDtoIn.getMicronPass(), aeSecretKey))
                .userType(userManagerDtoIn.getUserType())
                .tenantId(userManagerDtoIn.getTenantEntityId())
                .clubId(userManagerDtoIn.getClubEntityId())
                .build();
    }
}

