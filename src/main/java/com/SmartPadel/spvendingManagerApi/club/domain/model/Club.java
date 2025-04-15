package com.SmartPadel.spvendingManagerApi.club.domain.model;

import com.SmartPadel.spvendingManagerApi.externalUser.model.UserManager;
import com.SmartPadel.spvendingManagerApi.machines.modelsV1.Machine;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
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
    private List<Machine> machines;
    private List<UserManager> userManagers;

}
