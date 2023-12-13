package com.example.rgpdplatform.dto.response.answerResult;

import com.example.rgpdplatform.dto.AnswerDto;
import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.SubQuestionDto;
import com.example.rgpdplatform.dto.SuggestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsForAnswerDto {
    private QuestionDto question;
    private List<SuggestionDto> answers;

    public static  QuestionsForAnswerDto customMapping(AnswerDto answer){

        return QuestionsForAnswerDto.builder()
                .question(answer.getQuestion())
                .answers(answer.getAnswer())
                .build();
    }


}

