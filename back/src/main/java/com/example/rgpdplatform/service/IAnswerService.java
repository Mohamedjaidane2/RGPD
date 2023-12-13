package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.AnswerDto;
import com.example.rgpdplatform.dto.requests.create.AnswerNewDto;
import com.example.rgpdplatform.dto.requests.create.AnswersMultipleNewDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.answerResult.GetAllAnswersDto;

import java.util.List;

public interface IAnswerService {
    SuccessDto addAnswer(AnswerNewDto answerNewDto);

    AnswerDto getAnswerById(String answerId);

    List<AnswerDto> getAllAnswers();

    GetAllAnswersDto getAnswerByTest(String testId);

    SuccessDto addMultipleAnswers(AnswersMultipleNewDto dto);
}
