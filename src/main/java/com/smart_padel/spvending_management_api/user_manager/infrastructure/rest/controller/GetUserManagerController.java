package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.RetrieveUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutDetail;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutPreview;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping("/api/v1/user-managers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GetUserManagerController {
    private final RetrieveUserManagerUseCase retrieveUserManagerUseCase;
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity<Page<UserManagerDtoOutPreview>> getAllUserManagers(@RequestParam(required = false) String search,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<UserManagerDtoOutPreview> userManagers= retrieveUserManagerUseCase.getAllUserManager(search, pageable).map(UserManagerMapper::toDtoPreview);
        return new ResponseEntity<>(userManagers, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{userManagerId}")
    public ResponseEntity<UserManagerDtoOutDetail> getUserManagerById(@PathVariable UUID userManagerId) throws Exception {
        UserManager userManagerRequest=retrieveUserManagerUseCase.getUserManagerById(userManagerId);
        return new ResponseEntity<>(UserManagerMapper.toDtoDetail(userManagerRequest),HttpStatus.OK);
    }

}
