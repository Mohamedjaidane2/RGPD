package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.TopicDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetAllQuestionsDto {
    private TopicDto topic;
    private List<QuestionDto> questions;
}
