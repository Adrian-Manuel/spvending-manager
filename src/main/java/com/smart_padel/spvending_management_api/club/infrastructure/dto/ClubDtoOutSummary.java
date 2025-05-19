package com.smart_padel.spvending_management_api.club.infrastructure.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO containing the summary information of a club.")
public class ClubDtoOutSummary {
    @Schema(description = "UUID of the club", example = "12313432-54ab-4cde-1234-567812345678")
    private UUID clubId;
    @Schema(description = "Name of the club", example = "PadelPrix Ourense")
    private String name;
}
