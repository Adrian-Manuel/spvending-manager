package com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import java.util.UUID;
public interface UpdateTenantUseCase { Tenant updateTenant(UUID tenantId, Tenant updateTenant);}
