package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.RetrieveClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutDetail;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutPreview;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutSummary;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubPagePreviewSwagger;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.RetrieveMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutPreview;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
@Tag(name = "Club", description = "Retrieve club information")
public class GetClubController {
    private final RetrieveClubUseCase retrieveClubUseCase;
    private final RetrieveMachineUseCase retrieveMachineUseCase;

    @Operation(
            summary = "Get all clubs with pagination and optional search",
            description = "Returns a paginated list of clubs. Accesible to admin and user with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clubs retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ClubPagePreviewSwagger.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity<Page<ClubDtoOutPreview>> getAllClubs(
            @Parameter(description = "Search string to filter clubs by name or other criteria", example = "Padel")
            @RequestParam(required = false) String search,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClubDtoOutPreview> clubs = retrieveClubUseCase.getAllClubs(search, pageable).map(ClubMapper::toDtoPreview);
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    @Operation(
            summary = "Get club by ID",
            description = "Returns detailed information of a club by its UUID. Accessible to admin and user with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Club found",
                    content = @Content(schema = @Schema(implementation = ClubDtoOutDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Club not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDtoOutDetail> getClubById(
            @Parameter(description = "UUID of the club", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID clubId){
        Club clubRequest=retrieveClubUseCase.getClubById(clubId);
        return new ResponseEntity<>(ClubMapper.toDtoDetail(clubRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all machines by club ID",
            description = "Returns a list of machines associated with a specific club. Accessible to admin and user with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of machines for the club",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = MachineDtoOutPreview.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Club not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{clubId}/machines")
    public ResponseEntity<List<MachineDtoOutPreview>> getAllMachinesByClub(
            @Parameter(description = "UUID of the club", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID clubId){
        List<MachineDtoOutPreview> machines= retrieveMachineUseCase.findAllMachinesByClubId(clubId).stream().map(MachineMapper::toDtoPreview).toList();
        return new ResponseEntity<>(machines, HttpStatus.OK);
    }

    @Operation(
            summary = "Get summary of all clubs",
            description = "Returns a summary list of all clubs. Accessible to admin with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Summary list of clubs",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ClubDtoOutSummary.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/all-summary")
    public ResponseEntity<List<ClubDtoOutSummary>> getClubsSummary(){
        List<ClubDtoOutSummary> tenantsSummary=retrieveClubUseCase.getAllClubsSummary().stream().map(ClubMapper::toDtoSummary).toList();
        return new ResponseEntity<>(tenantsSummary, HttpStatus.OK);
    }
}

