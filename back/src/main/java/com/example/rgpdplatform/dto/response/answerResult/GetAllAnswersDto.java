package com.example.rgpdplatform.dto.response.answerResult;

import com.example.rgpdplatform.dto.TestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAnswersDto {

    private TestDto test;
    private List<TopicForAnswerDto> topic;
}
