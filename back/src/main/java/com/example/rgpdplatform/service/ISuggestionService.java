package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.requests.create.SuggestionNewDto;
import com.example.rgpdplatform.dto.requests.update.SuggestionUpdateDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.SuggestionRefDto;
import com.example.rgpdplatform.model.Suggestion;

import java.util.List;

public interface ISuggestionService {
    SuccessDto addSuggestion(SuggestionNewDto suggestionNewDto);

    SuccessDto updateSuggestion(SuggestionUpdateDto suggestionDto);


    SuggestionDto getSuggestionById(String suggestionId);

    List<Suggestion> getAllSuggestions();

    SuccessDto deleteSuggestionByRef(String suggestionRef);

    SuccessDto deleteSuggestionById(String suggestionId);

    List<SuggestionRefDto> getSuggestionNamesByTopicId(String topicId);
}
