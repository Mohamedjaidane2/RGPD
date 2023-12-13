package com.example.rgpdplatform.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Data
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "suggestions")
public class Suggestion extends AbstractDocument {

    private String title;

    private String refS;

    @DocumentReference(lazy = true)
    private GradingScale gradingScale ;
    @Override
    public String toString() {
        return "Guest{" +
                "title='" + title + '\'' +
                ", refS='" + refS + '\'' +
                '}';
    }
}
