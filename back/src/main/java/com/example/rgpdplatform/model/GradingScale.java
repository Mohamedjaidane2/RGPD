package com.example.rgpdplatform.model;

import com.example.rgpdplatform.enums.Penalty_status_enum;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Data
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "grading_scales")
public class GradingScale extends AbstractDocument {

    private String title;

    private Float penaltyJail;

    private Float penaltyAmount;

    private Penalty_status_enum penaltyStatus;

    @Override
    public String toString() {
        return "GradingScale{" +
                "title='" + title + '\'' +
                ", penaltyJail='" + penaltyJail + '\'' +
                ", penaltyAmount='" + penaltyAmount + '\'' +
                ", penaltyStatus='" + penaltyStatus + '\'' +
                '}';
    }
}
