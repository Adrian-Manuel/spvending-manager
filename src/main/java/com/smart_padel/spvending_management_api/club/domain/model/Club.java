package com.smart_padel.spvending_management_api.club.domain.model;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity.UserManagerEntity;
import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.entity.MachineEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Club {
    private UUID clubId;
    private String name;
    private String cif;
    private String address;
    private String phone;
    private String email;
    private String remark;
    private String accountingId;
    private String micronId;
    private TenantEntity tenantEntity;
    private List<MachineEntity> machineEntities;
    private List<UserManagerEntity> userManagerEntities;
}
