package com.smart_padel.spvending_management_api.user_manager.domain.ports.in;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import java.util.UUID;
public interface UpdateUserManagerUseCase { UserManager updateUserManager(UUID userManagerId,UserManager userManager);}
