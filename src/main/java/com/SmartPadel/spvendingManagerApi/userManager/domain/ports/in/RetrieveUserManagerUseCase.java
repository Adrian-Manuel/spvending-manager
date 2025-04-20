package com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RetrieveUserManagerUseCase {
    UserManager getUserManagerById(UUID userManagerId);
    Page<UserManager> getAllUserManager(String search, Pageable pageable);

}