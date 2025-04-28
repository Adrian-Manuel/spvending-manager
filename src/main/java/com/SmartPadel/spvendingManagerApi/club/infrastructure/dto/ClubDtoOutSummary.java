package com.SmartPadel.spvendingManagerApi.club.infrastructure.dto;
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
public class ClubDtoOutSummary {
    @Schema(description = "Identificator of the club ")
    private UUID clubId;
    @Schema(description = "Name of the club", example = "PadelPrix Ourense")
    private String name;
}
