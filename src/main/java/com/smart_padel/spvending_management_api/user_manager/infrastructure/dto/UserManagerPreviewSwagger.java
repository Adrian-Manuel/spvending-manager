package com.smart_padel.spvending_management_api.user_manager.infrastructure.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UserManagerPreviewSwagger {
    @Schema(description = "UserManager in this page")
    public List<UserManagerDtoOutPreview> content;

    @Schema(description = "Page info")
    public PageInfo page;

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
