package com.example.rgpdplatform.model;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tests")
public class Test extends AbstractDocument {

    @Override
    public String toString() {
        return "Test{" +
                "ref='" + refT + '\'' +
                ", score=" + score +
                ", guest=" + guest +
                '}';
    }

    private String refT;

    private Float score;

    private Float totalPenaltyJail;

    private Float totalPenaltyAmount;

    private Integer totalCorrectAnswers;

    @DocumentReference(lazy = false)
    private Guest guest;


}
