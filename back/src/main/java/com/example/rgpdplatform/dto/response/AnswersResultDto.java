package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.dto.AnswerDto;
import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.TestDto;
import com.example.rgpdplatform.model.Answer;
import com.example.rgpdplatform.model.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AnswersResultDto {
    private Float score;

    private Date created;

    private TestDto test;

    private TopicsResultDto topicsResult;

    @Override
    public String toString() {
        return "Answer{" +
                "score=" + score +
                ", test='" + test + '\'' +
                ", topicsResult=" + topicsResult +
                '}';
    }
    public static AnswersResultDto customMapping(Answer answer){
        return AnswersResultDto.builder()
                .score(answer.getScore())
                .test(TestDto.customMapping(answer.getTest()))
                .topicsResult(null)
                .created(answer.getCreationDate())
                .build();
    }
}
