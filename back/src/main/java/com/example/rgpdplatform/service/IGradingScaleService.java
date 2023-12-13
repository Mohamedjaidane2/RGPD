package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.requests.create.GradingScaleNewDto;
import com.example.rgpdplatform.dto.GradingScaleDto;
import com.example.rgpdplatform.dto.response.IdDto;
import com.example.rgpdplatform.dto.response.SuccessDto;

import java.util.List;

public interface IGradingScaleService {
    IdDto add(GradingScaleNewDto gradingScaleNewDto);

    GradingScaleDto getGradingScaleById(String answerId);

    List<GradingScaleDto> getAllGradingScales();

    GradingScaleDto getBySuggestion(String suggestionId);

    SuccessDto delete(String gradingScaleId);

    SuccessDto updateGradingScale(String gradingScaleId, GradingScaleNewDto gradingScaleNewDto);
}
