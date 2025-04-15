package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto;

import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;


public class TenantMapper {
    public static TenantDtoOutPreview toDtoPreview(Tenant tenant) {
        if ( tenant == null ) {
            return null;
        }

        TenantDtoOutPreview.TenantDtoOutPreviewBuilder tenantDtoOutPreview = TenantDtoOutPreview.builder();
        tenantDtoOutPreview.tenantId( tenant.getTenantId() );
        tenantDtoOutPreview.name( tenant.getName() );
        tenantDtoOutPreview.cif( tenant.getCif() );
        tenantDtoOutPreview.address( tenant.getAddress() );
        tenantDtoOutPreview.phone( tenant.getPhone() );
        tenantDtoOutPreview.email( tenant.getEmail() );
        tenantDtoOutPreview.remark( tenant.getRemark() );
        tenantDtoOutPreview.micronId( tenant.getMicronId() );

        return tenantDtoOutPreview.build();
    }

    public static Tenant toModel(TenantDtoIn tenantDto) {
        if ( tenantDto == null ) {
            return null;
        }

        Tenant.TenantBuilder tenant = Tenant.builder();

        tenant.name( tenantDto.getName() );
        tenant.cif( tenantDto.getCif() );
        tenant.address( tenantDto.getAddress() );
        tenant.phone( tenantDto.getPhone() );
        tenant.email( tenantDto.getEmail() );
        tenant.remark( tenantDto.getRemark() );
        tenant.micronId( tenantDto.getMicronId() );

        return tenant.build();
    }
}



