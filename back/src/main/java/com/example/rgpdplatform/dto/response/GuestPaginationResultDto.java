package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.dto.GuestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GuestPaginationResultDto {
    private Integer count;
    private List<GuestDto> guests;
}
