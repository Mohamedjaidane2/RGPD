package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.dto.AnswerDto;
import com.example.rgpdplatform.dto.TopicDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopicsResultDto extends TopicDto {

    private List<QuestionResultDto> questionsAnswers;


}
