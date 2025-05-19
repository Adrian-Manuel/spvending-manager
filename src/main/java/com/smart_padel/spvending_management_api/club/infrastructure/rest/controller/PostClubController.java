package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.CreateClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoIn;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutPreview;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
@Tag(name = "Club", description = "Create a new club")
public class PostClubController {
   private final CreateClubUseCase createClubUseCase;

    @Operation(
            summary = "Create a new club",
            description = "Creates a new club and returns its preview information. Requires 'admin:create' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Club created successfully",
                    content = @Content(schema = @Schema(implementation = ClubDtoOutPreview.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<ClubDtoOutPreview> createClub (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Club information for creation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClubDtoIn.class))
            )
            @RequestBody @Valid ClubDtoIn clubDtoIn){
        Club clubRequest= ClubMapper.toModel(clubDtoIn);
        clubRequest=createClubUseCase.createClub(clubDtoIn.getTenantId(), clubRequest);
        return new ResponseEntity<>(ClubMapper.toDtoPreview(clubRequest), HttpStatus.CREATED);
    }
}
