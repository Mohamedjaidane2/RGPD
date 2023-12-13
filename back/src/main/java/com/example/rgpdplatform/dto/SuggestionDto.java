package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.model.Test;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SuggestionDto {

    private String id;

    private String title;

    private String refS;

    private String gradingScaleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    public static SuggestionDto customMapping(Suggestion suggestion){
        return SuggestionDto.builder()
                .id(suggestion.getId())
                .created(suggestion.getCreationDate())
                .refS(suggestion.getRefS())
                .title(suggestion.getTitle())
                .build();
    }

    public static List<SuggestionDto> customListMapping(List<Suggestion> suggestions){
        if (suggestions == null) return null;
        ArrayList<SuggestionDto> suggestionsDto = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            SuggestionDto suggestionDto = customMapping(suggestion);
            suggestionsDto.add(suggestionDto);
        }
        return suggestionsDto;
    }
}
