package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.ports.in.DeleteClubUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
@Tag(name= "Club", description = "Delete club information")
public class DeleteClubController {
    private final DeleteClubUseCase deleteClubUseCase;

    @Operation(
            summary = "Delete club by ID",
            description = "Deletes a club by its UUID. Requires 'admin:delete' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Club deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Club not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClubById(
            @Parameter(description = "UUID of the club to delete", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID clubId) {
        deleteClubUseCase.deleteClub(clubId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}