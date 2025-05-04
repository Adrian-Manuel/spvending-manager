package com.smart_padel.spvending_management_api.user_manager.domain.ports.in;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import java.security.GeneralSecurityException;
import java.util.UUID;
public interface CreateUserManagerUseCase {UserManager createUserManager(UUID tenantId, UUID clubId,UserManager userManager) throws GeneralSecurityException;}
