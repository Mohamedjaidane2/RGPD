package com.example.rgpdplatform.model;

import com.example.rgpdplatform.enums.Type_enum;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Question extends AbstractDocument {

    private String title;

    private Type_enum type;

    private String refQ;

    @DocumentReference(lazy = true)
    private List<Suggestion> suggestionSrc;

    @DocumentReference(lazy = true)
    private Topic topic;

    @DocumentReference(lazy = true)
    private List<Question> subQuestions;

    @DocumentReference(lazy = true)
    private List<Suggestion> suggestions;


    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", topic=" + topic +
                ", subQuestions=" + subQuestions +
                ", suggestions=" + suggestions +
                '}';
    }

}
