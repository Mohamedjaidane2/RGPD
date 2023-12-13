package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.dto.GuestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TestResultDto {
    private String id;

    private String refT;

    private Float score;

    private GuestDto guest;
}
