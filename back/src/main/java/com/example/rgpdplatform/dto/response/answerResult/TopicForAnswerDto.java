package com.example.rgpdplatform.dto.response.answerResult;

import com.example.rgpdplatform.dto.TopicDto;
import com.example.rgpdplatform.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicForAnswerDto {
    private TopicDto topic;
    private List<QuestionsForAnswerDto> questions;
}
