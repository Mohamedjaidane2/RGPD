package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.enums.Penalty_status_enum;
import com.example.rgpdplatform.model.GradingScale;
import com.example.rgpdplatform.model.Question;
import com.example.rgpdplatform.model.Suggestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GradingScaleDto {

    private String id;

    private Float penaltyJail;

    private Float penaltyAmount;

    private Penalty_status_enum penaltyStatus;

    public static GradingScaleDto customMapping(GradingScale gradingScale){
        return GradingScaleDto.builder()
                .id(gradingScale.getId())
                .penaltyStatus(gradingScale.getPenaltyStatus())
                .penaltyAmount(gradingScale.getPenaltyAmount())
                .penaltyJail(gradingScale.getPenaltyJail())
                .build();

    }

}
