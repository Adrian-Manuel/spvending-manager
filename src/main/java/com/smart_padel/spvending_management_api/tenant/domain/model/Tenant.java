package com.smart_padel.spvending_management_api.tenant.domain.model;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
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
    private List<Club> clubs;
    private List <UserManager> userManagers;
}
