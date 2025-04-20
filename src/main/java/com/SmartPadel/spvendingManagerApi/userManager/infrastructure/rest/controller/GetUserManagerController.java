package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.RetrieveUserManagerUseCase;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.mapper.UserManagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-managers")
@RequiredArgsConstructor
public class GetUserManagerController {

    private final RetrieveUserManagerUseCase retrieveUserManagerUseCase;

    @GetMapping
    public ResponseEntity<Page<UserManagerDtoOutPreview>> getAllUserManagers(@RequestParam(required = false) String search,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<UserManagerDtoOutPreview> userManagers= retrieveUserManagerUseCase.getAllUserManager(search, pageable).map(UserManagerMapper::toDtoPreview);
        return new ResponseEntity<>(userManagers, HttpStatus.OK);
    }

}
