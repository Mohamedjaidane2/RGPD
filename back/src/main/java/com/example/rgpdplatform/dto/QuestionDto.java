package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.enums.Type_enum;
import com.example.rgpdplatform.model.Question;
import com.example.rgpdplatform.model.Suggestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuestionDto {

    private String id;

    private String title;

    private Type_enum type;

    private String refQ;

    private Integer intRefQ;

    private TopicDto topic;

    private List<Suggestion> suggestionSrc;

    private List<SubQuestionDto> subQuestions ;

    private List<SuggestionDto> suggestions;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    public static QuestionDto customMapping(Question question){
        return QuestionDto.builder()
                .id(question.getId())
                .refQ(question.getRefQ())
                .title(question.getTitle())
                .type(question.getType())
                .created(question.getCreationDate())
                .topic(TopicDto.customMapping(question.getTopic()))
                .suggestionSrc(question.getSuggestionSrc())
                .subQuestions(SubQuestionDto.customListMapping(question.getSubQuestions()))
                .suggestions(SuggestionDto.customListMapping(question.getSuggestions()))
               // .suggestionSrc(SuggestionDto.customMapping(question.getSuggestionSrc()))
                .build();
    }
    public static QuestionDto smallCustomMapping(Question question){
        return QuestionDto.builder()
                .refQ(question.getRefQ())
                .id(question.getId())
                .title(question.getTitle())
                .type(question.getType())
                .created(question.getCreationDate())
                .topic(TopicDto.customMapping(question.getTopic()))
                .subQuestions(SubQuestionDto.customListMapping(question.getSubQuestions()))
                .build();
    }


    public static List<QuestionDto> customListMapping(List<Question> questions){
        if (questions == null) return null;
        ArrayList<QuestionDto> questionsDto = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = customMapping(question);
            questionsDto.add(questionDto);
        }
        return questionsDto;
    }


    //Custom mapping for answer result;
    public static QuestionDto customAnswerMapping(Question question){
        return QuestionDto.builder()
                .id(question.getId())
                .refQ(question.getRefQ())
                .title(question.getTitle())
                .type(question.getType())
                .created(question.getCreationDate())
                .subQuestions(SubQuestionDto.customListMapping(question.getSubQuestions()))
                .suggestions(SuggestionDto.customListMapping(question.getSuggestions()))
                .build();
    }
}
