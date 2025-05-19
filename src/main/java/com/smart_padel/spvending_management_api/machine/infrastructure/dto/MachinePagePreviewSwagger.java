package com.smart_padel.spvending_management_api.machine.infrastructure.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Paginated response of MachineDtoOutPreview")
public class MachinePagePreviewSwagger {
    @Schema(description = "Machines in this page")
    public List<MachineDtoOutPreview> content;

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
