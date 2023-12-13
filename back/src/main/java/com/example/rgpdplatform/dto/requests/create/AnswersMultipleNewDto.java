package com.example.rgpdplatform.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AnswersMultipleNewDto {

    private String testId;
    private List<MultipleAnswers> answers;
}
