package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.dto.AnswerDto;
import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.SuggestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuestionResultDto {
    private QuestionDto question;
    private List<SuggestionDto> answer;
}
