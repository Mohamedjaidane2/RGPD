package com.example.rgpdplatform.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationDto {
    private Integer pageSize;
    private Integer pageNumber;
}
