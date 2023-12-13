package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AnswerDto {

    private String id;

    private Float score;

    private TestDto test;

    private TopicDto topic;

    private QuestionDto question;

    private List<SuggestionDto> answer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    @Override
    public String toString() {
        return "Answer{" +
                "score=" + score +
                ", test='" + test + '\'' +
                ", question=" + question +
                ", answers=" + answer +
                '}';
    }
    public static AnswerDto customMapping(Answer answer){
        return AnswerDto.builder()
                .id(answer.getId())
                .topic(TopicDto.customMapping(answer.getTopic()))
                .answer(SuggestionDto.customListMapping(answer.getAnswer()))
                .question(QuestionDto.customAnswerMapping(answer.getQuestion()))
                .test(TestDto.customMapping(answer.getTest()))
                .created(answer.getCreationDate())
                .build();
    }
}
