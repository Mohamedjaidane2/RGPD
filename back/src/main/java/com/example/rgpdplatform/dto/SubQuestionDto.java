package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.enums.Type_enum;
import com.example.rgpdplatform.model.Question;
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
public class SubQuestionDto {

    private String id;

    private String title;

    private Type_enum type;

    private String refQ;

    private List<SubQuestionDto> subQuestions ;

    private List<SuggestionDto> suggestions;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    public static SubQuestionDto customMapping(Question question){
        return SubQuestionDto.builder()
                .id(question.getId())
                .refQ(question.getRefQ())
                .title(question.getTitle())
                .type(question.getType())
                .created(question.getCreationDate())
                .subQuestions(SubQuestionDto.customListMapping(question.getSubQuestions()))
                .suggestions(SuggestionDto.customListMapping(question.getSuggestions()))
                .build();
    }

    public static List<SubQuestionDto> customListMapping(List<Question> subQuestions){
        if (subQuestions == null) return null;
        ArrayList<SubQuestionDto> subQuestionsDto = new ArrayList<>();
        for (Question question : subQuestions) {
            SubQuestionDto questionDto = customMapping(question);
            subQuestionsDto.add(questionDto);
        }
        return subQuestionsDto;
    }
}
