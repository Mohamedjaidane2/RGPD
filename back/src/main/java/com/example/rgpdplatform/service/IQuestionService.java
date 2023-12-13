package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.requests.create.SubQuestionNewDto;
import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.requests.create.QuestionNewDto;
import com.example.rgpdplatform.dto.requests.update.QuestionUpdateDto;
import com.example.rgpdplatform.dto.response.GetAllQuestionsDto;
import com.example.rgpdplatform.dto.response.SuccessDto;

import java.util.List;

public interface IQuestionService {

    SuccessDto addQuestion(QuestionNewDto questionNewDto);

    SuccessDto updateQuestion(QuestionUpdateDto questionDto);

    QuestionDto getQuestionByTitle(String questionTitle);

    QuestionDto getQuestionById(String questionId);

    List<GetAllQuestionsDto> getAllQuestions();

    List<QuestionDto> getAllQuestionRef();

    List<QuestionDto> getQuestionByTopic(String topicId);

    List<SuggestionDto> getSuggestionListByQuestion(String title);

    List<QuestionDto> getSubQuestionListByQuestionId(String questionId);

    SuccessDto addSubQuestions( String questionId, List<SubQuestionNewDto> SubQuestionNewDto);

    SuccessDto deleteById(String questionId);
}
