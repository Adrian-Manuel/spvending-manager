package com.smart_padel.spvending_management_api.tenant.infrastructure.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Paginated response of TenantDtoOutPreview")
public class TenantPagePreviewSwagger {
    @Schema(description = "Tenants in this page")
    public List<TenantDtoOutPreview> content;

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
