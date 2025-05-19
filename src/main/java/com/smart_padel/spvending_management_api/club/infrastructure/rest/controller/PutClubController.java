package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.UpdateClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoIn;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutDetail;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clubs")
@Tag(name = "Club", description = "Update club information")
public class PutClubController {
    private final UpdateClubUseCase updateClubUseCase;

    @Operation(
            summary = "Update a club",
            description = "Updates the information of a club by its ID. Requires 'admin:update' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Club updated successfully",
                    content = @Content(schema = @Schema(implementation = ClubDtoOutDetail.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Club not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{clubId}")
    public ResponseEntity<ClubDtoOutDetail> updateClub(
            @Parameter(description = "UUID of the club to update", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID clubId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Club information for update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClubDtoIn.class))
            )
            @Valid @RequestBody ClubDtoIn clubDtoIn){
        Club clubRequest= ClubMapper.toModel(clubDtoIn);
        clubRequest=updateClubUseCase.updateClub(clubDtoIn.getTenantId(),clubId, clubRequest);
        return new ResponseEntity<>(ClubMapper.toDtoDetail(clubRequest), HttpStatus.OK);
    }

}
