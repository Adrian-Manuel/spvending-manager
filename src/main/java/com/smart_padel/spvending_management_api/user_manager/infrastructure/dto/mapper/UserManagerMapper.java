package com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper;
import com.smart_padel.spvending_management_api.shared.utils.AESGCMEncryption;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutDetail;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutPreview;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserManagerMapper {

    static AESGCMEncryption aesgcmEncryption;

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
                .password(aesgcmEncryption.decrypt(userManager.getPassword()))
                .micronId(userManager.getMicronId())
                .micronUser(userManager.getMicronUser())
                .micronPass(aesgcmEncryption.decrypt(userManager.getMicronPass()))
                .userType(userManager.getUserType())
                .clubEntityName(userManager.getClub() != null ? userManager.getClub().getName() : null)
                .tenantEntityName(userManager.getTenantEntity() != null ? userManager.getTenantEntity().getName() : null)
                .build();
    }
    public static UserManager toModel(UserManagerDtoIn userManagerDtoIn) throws Exception {
        return UserManager.builder()
                .userName(userManagerDtoIn.getUsername())
                .password(aesgcmEncryption.encrypt(userManagerDtoIn.getPassword()))
                .micronId(userManagerDtoIn.getMicronId())
                .micronUser(userManagerDtoIn.getMicronUser())
                .micronPass(aesgcmEncryption.encrypt(userManagerDtoIn.getMicronPass()))
                .userType(userManagerDtoIn.getUserType())
                .build();
    }
}

