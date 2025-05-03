package com.smart_padel.spvending_management_api.user_manager.application.usecases;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.RetrieveUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class RetrieveUserManagerUseCaseImpl implements RetrieveUserManagerUseCase {
    private final UserManagerRepositoryPort userManagerRepositoryPort;
    @Override
    public UserManager getUserManagerById(UUID userManagerId) {
        return userManagerRepositoryPort.findById(userManagerId);
    }
    @Override
    public Page<UserManager> getAllUserManager(String search, Pageable pageable) {
        return userManagerRepositoryPort.findAll(search, pageable);
    }
}
