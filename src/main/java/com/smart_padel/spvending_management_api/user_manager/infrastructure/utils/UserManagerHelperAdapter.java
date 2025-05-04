package com.smart_padel.spvending_management_api.user_manager.infrastructure.utils;
import com.smart_padel.spvending_management_api.shared.exceptions.ParamRequiredException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.repository.JpaUserManagerRepository;
import java.util.UUID;
public class UserManagerHelperAdapter {
    private UserManagerHelperAdapter() {
        throw new IllegalStateException("Util class");
    }
    public static void validateClubOrTenant(UUID tenantId, UUID clubId, String userType) {
        if (tenantId == null && clubId == null) {
            throw new ParamRequiredException("some club id or tenant id is required");
        }
        if (tenantId != null && clubId != null) {
            throw new ParamRequiredException("only one of the two parameters is needed");
        }
        if (tenantId != null && "1".equals(userType)) {
            throw new ParamRequiredException("The user is type 1, the club id is required.");
        }
        if (clubId != null && "2".equals(userType)) {
            throw new ParamRequiredException("The user is type 2, the tenant id is required.");
        }
    }
    public static void validateUserUniqueness(JpaUserManagerRepository repo, UserManager userManager) {
        if (repo.existsByUserName(userManager.getUserName())) {
            throw new ResourceAlreadyExistsException("a user with that name already exists");
        }
        if (repo.existsByMicronId(userManager.getMicronId())) {
            throw new ResourceAlreadyExistsException("a user with that micron Id already exists");
        }
        if (repo.existsByMicronUser(userManager.getMicronUser())) {
            throw new ResourceAlreadyExistsException("a user with that micron user already exists");
        }
    }
    public static void validateUserManagerExists(JpaUserManagerRepository repo, UUID userManagerId) {
        if (!repo.existsById(userManagerId)) {
            throw new ResourceNotFoundException("The club does not exist");
        }
    }


}
