package com.example.rgpdplatform.dto.requests.create;

import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.TestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AnswerNewDto {

    private String test_id;

    private String question_id;

    private List<String> suggestion_ids;

}
