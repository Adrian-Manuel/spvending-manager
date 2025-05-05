package com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutSummary;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoIn;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutDetail;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutPreview;
public class TenantMapper {
    private TenantMapper() {
        throw new IllegalStateException("Mapper class");
    }
    public static TenantDtoOutPreview toDtoPreview(Tenant tenant) {
        return TenantDtoOutPreview.builder()
                .tenantId( tenant.getTenantId() )
                .cif( tenant.getCif() )
                .name( tenant.getName() )
                .phone( tenant.getPhone() )
                .email( tenant.getEmail() )
                .clubsCount(tenant.getClubsCount())
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
                .managers(tenant.getManagers())
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
    public static TenantDtoOutSummary toDtoSummary(Tenant tenant){
        return TenantDtoOutSummary.builder()
                .name(tenant.getName())
                .tenantId(tenant.getTenantId())
                .build();
    }
}



