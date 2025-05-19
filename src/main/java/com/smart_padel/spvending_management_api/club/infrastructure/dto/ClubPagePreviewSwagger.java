package com.smart_padel.spvending_management_api.club.infrastructure.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Paginated response of ClubDtoOutPreview")
public class ClubPagePreviewSwagger {
    @Schema(description = "Clubs in this page")
    public List<ClubDtoOutPreview> content;

    @Schema(description = "Page info")
    public ClubPagePreviewSwagger.PageInfo page;

    @Schema(description = "Pagination metadata")
    public static class PageInfo {
        @Schema(example = "10")
        public int size;
        @Schema(example = "0")
        public int number;
        @Schema(example = "1")
        public int totalElements;
        @Schema(example = "1")
        public int totalPages;
    }
}
