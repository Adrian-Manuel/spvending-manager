package com.smart_padel.spvending_management_api.tenant.domain.model;
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
public class Tenant {
    private UUID tenantId;
    private String name;
    private String cif;
    private String address;
    private String phone;
    private String email;
    private String remark;
    private String micronId;
    private int clubsCount;
    private List<String> managers;
}
