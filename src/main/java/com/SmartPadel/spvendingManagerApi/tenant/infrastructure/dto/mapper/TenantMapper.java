package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.mapper;

import com.SmartPadel.spvendingManagerApi.externalUser.model.UserManager;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoIn;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoOutPreview;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


public class TenantMapper {
    public static TenantDtoOutPreview toDtoPreview(Tenant tenant) {
        return TenantDtoOutPreview.builder()
                .tenantId( tenant.getTenantId() )
                .cif( tenant.getCif() )
                .name( tenant.getName() )
                .phone( tenant.getPhone() )
                .email( tenant.getEmail() )
                .clubsCount((tenant.getClubs()!=null)?tenant.getClubs().size():0)
                .build();

    }

    public static TenantDtoOutDetail toDtoDetail(Tenant tenant) {
        return TenantDtoOutDetail.builder()
                .tenantId( tenant.getTenantId() )
                .cif( tenant.getCif() )
                .name( tenant.getName() )
                .phone( tenant.getPhone() )
                .email( tenant.getEmail() )
                .address(tenant.getAddress())
                .remark(tenant.getRemark())
                .micronId(tenant.getMicronId())
                .managers(tenant.getUserManagers() !=null ? tenant.getUserManagers()
                        .stream()
                        .map(UserManager::getUsername)
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }



    public static Tenant toModel(TenantDtoIn tenantDto) {
        return Tenant.builder()
                .name( tenantDto.getName() )
                .cif( tenantDto.getCif() )
                .address( tenantDto.getAddress() )
                .phone( tenantDto.getPhone() )
                .email( tenantDto.getEmail() )
                .remark( tenantDto.getRemark() )
                .micronId( tenantDto.getMicronId() )
                .build();
    }
}



