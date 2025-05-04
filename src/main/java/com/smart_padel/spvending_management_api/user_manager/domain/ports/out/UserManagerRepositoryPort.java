package com.smart_padel.spvending_management_api.user_manager.domain.ports.out;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.security.GeneralSecurityException;
import java.util.UUID;
public interface UserManagerRepositoryPort {
    UserManager save (UUID tenantId, UUID clubId, UserManager userManager) throws GeneralSecurityException;
    UserManager update(UUID tenantId, UUID clubId, UUID userManagerId, UserManager userManager);
    void delete(UUID userManagerId);
    Page<UserManager> findAll(String search, Pageable pageable);
    UserManager findById(UUID userManager);
}
