package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.dto.TestDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestPaginationResultDto {
    private Integer count;
    private List<TestDto> tests;
}
