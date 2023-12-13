package com.example.rgpdplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorDto {


    private Integer totalCorrectAnswers;

    private Float penaltyJail;

    private Float penaltyAmount;

}
