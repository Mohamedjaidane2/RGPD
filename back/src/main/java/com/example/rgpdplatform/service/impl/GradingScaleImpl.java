package com.example.rgpdplatform.service.impl;

import com.example.rgpdplatform.dto.requests.create.GradingScaleNewDto;
import com.example.rgpdplatform.dto.GradingScaleDto;
import com.example.rgpdplatform.dto.response.IdDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.GradingScale;
import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.repository.IGradingScaleRepository;
import com.example.rgpdplatform.repository.ISuggestionRepository;
import com.example.rgpdplatform.service.IGradingScaleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.*;

@Service
public class GradingScaleImpl implements IGradingScaleService {

    private final IGradingScaleRepository iGradingScaleRepository;
    private final ISuggestionRepository iSuggestionRepository;

    public GradingScaleImpl(IGradingScaleRepository iGradingScaleRepository, ISuggestionRepository iSuggestionRepository) {
        this.iGradingScaleRepository = iGradingScaleRepository;
        this.iSuggestionRepository = iSuggestionRepository;
    }

    @Override
    public IdDto add(GradingScaleNewDto gradingScaleDto) {
        GradingScale gradingScale =GradingScale.builder()
                .penaltyAmount(gradingScaleDto.getPenaltyAmount())
                .penaltyJail(gradingScaleDto.getPenaltyJail())
                .penaltyStatus(gradingScaleDto.getPenaltyStatus())
                .build();
        iGradingScaleRepository.save(gradingScale);
        return IdDto.builder()
                .id(gradingScale.getId())
                .build();
    }

    @Override
    public GradingScaleDto getGradingScaleById(String gradingScale) {
        Optional<GradingScale> checkIfExist = iGradingScaleRepository.findById(gradingScale);
        if(checkIfExist.isEmpty())
            throw new InvalidOperationException("Grading Not Found!", ErrorCodes.GRADING_SCALE_NOT_FOUND);
        return GradingScaleDto.customMapping(checkIfExist.get());
    }

    @Override
    public List<GradingScaleDto> getAllGradingScales() {
        return iGradingScaleRepository.findAll()
                .stream()
                .map(GradingScaleDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public GradingScaleDto getBySuggestion(String suggestionId) {
        Optional<Suggestion> checkSuggestion = iSuggestionRepository.findById(suggestionId);
        if(checkSuggestion.isEmpty())
            throw new EntityNotFoundException("Suggestion not found!",ErrorCodes.SUGGESTION_NOT_FOUND);
        if(checkSuggestion.get().getGradingScale() == null)
            throw new EntityNotFoundException("Grading scale not found for this suggestion!",ErrorCodes.GRADING_SCALE_NOT_FOUND);

        return GradingScaleDto.customMapping(checkSuggestion.get().getGradingScale());
    }

    @Override
    public SuccessDto updateGradingScale(String gradingScaleId, GradingScaleNewDto gradingScaleNewDto) {
        Optional<GradingScale> gradingScale = iGradingScaleRepository.findById(gradingScaleId);
        if(gradingScale.isEmpty())
            throw new EntityNotFoundException("Grading scale not found!",ErrorCodes.GRADING_SCALE_NOT_FOUND);

        GradingScale gradingScaleToUpdate = gradingScale.get();
        gradingScaleToUpdate.setPenaltyAmount(gradingScaleNewDto.getPenaltyAmount());
        gradingScaleToUpdate.setPenaltyStatus(gradingScaleNewDto.getPenaltyStatus());
        gradingScaleToUpdate.setPenaltyJail(gradingScaleNewDto.getPenaltyJail());
        iGradingScaleRepository.save(gradingScaleToUpdate);
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public SuccessDto delete(String gradingScaleId) {
        Optional<GradingScale> gradingScale = iGradingScaleRepository.findById(gradingScaleId);
        if(gradingScale.isEmpty())
            throw new EntityNotFoundException("Grading scale not found!",ErrorCodes.GRADING_SCALE_NOT_FOUND);
        Optional<List<Suggestion>> suggestions = iSuggestionRepository.findAllByGradingScale_Id(gradingScaleId);
        if(suggestions.isPresent())
            throw new InvalidOperationException("this Grading scale is in use by one or more suggestions",ErrorCodes.GRADING_SCALE_IN_USE);
        iGradingScaleRepository.delete(gradingScale.get());
        return SuccessDto
                .builder()
                .message(GRADING_SCALE_SUCCESSFULLY_DELETED)
                .build();
    }

}
