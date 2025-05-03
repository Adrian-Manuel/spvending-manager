package com.smart_padel.spvending_management_api.tenant.domain.ports.in;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import java.util.UUID;
public interface UpdateTenantUseCase { Tenant updateTenant(UUID tenantId, Tenant updateTenant);}
