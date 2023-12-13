package com.example.rgpdplatform.dto.requests.create;

import com.example.rgpdplatform.enums.Penalty_status_enum;
import lombok.Data;

@Data
public class GradingScaleNewDto {
    private Float penaltyJail;
    private Float penaltyAmount;
    private Penalty_status_enum penaltyStatus;
}
