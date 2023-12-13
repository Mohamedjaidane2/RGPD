package com.example.rgpdplatform.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "answers")
public class Answer extends AbstractDocument {

    private Float score;

    @DocumentReference(lazy = true)
    private Test test;

    @DocumentReference(lazy = true)
    private Topic topic;

    @DocumentReference(lazy = true)
    private Question question;

    @DocumentReference(lazy = true)
    private List<Suggestion> answer;

    @Override
    public String toString() {
        return "Answer{" +
                "score=" + score +
                ", test='" + test + '\'' +
                ", question=" + question +
                ", answers=" + answer +
                '}';
    }
}
