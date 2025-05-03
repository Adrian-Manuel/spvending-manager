package com.smart_padel.spvending_management_api.user_manager.domain.ports.in;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;
public interface RetrieveUserManagerUseCase {
    UserManager getUserManagerById(UUID userManagerId);
    Page<UserManager> getAllUserManager(String search, Pageable pageable);
}