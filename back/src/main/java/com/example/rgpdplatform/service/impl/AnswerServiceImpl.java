package com.example.rgpdplatform.service.impl;

import com.example.rgpdplatform.dto.AnswerDto;
import com.example.rgpdplatform.dto.requests.create.AnswerNewDto;
import com.example.rgpdplatform.dto.TestDto;
import com.example.rgpdplatform.dto.TopicDto;
import com.example.rgpdplatform.dto.requests.create.AnswersMultipleNewDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.answerResult.GetAllAnswersDto;
import com.example.rgpdplatform.dto.response.answerResult.QuestionsForAnswerDto;
import com.example.rgpdplatform.dto.response.answerResult.TopicForAnswerDto;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.model.*;
import com.example.rgpdplatform.repository.*;
import com.example.rgpdplatform.service.IAnswerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.SUCCESSFULLY_CREATED;

@Service
public class AnswerServiceImpl implements IAnswerService {

    private final IAnswerRepository iAnswerRepository;
    private final ITestRepository iTestRepository;
    private final IQuestionRepository iQuestionRepository;
    private final ISuggestionRepository iSuggestionRepository;

    public AnswerServiceImpl(IAnswerRepository iAnswerRepository, ITestRepository iTestRepository, IQuestionRepository iQuestionRepository, ISuggestionRepository iSuggestionRepository, IGradingScaleRepository iGradingScaleRepository) {

        this.iAnswerRepository = iAnswerRepository;
        this.iTestRepository = iTestRepository;
        this.iQuestionRepository = iQuestionRepository;
        this.iSuggestionRepository = iSuggestionRepository;
    }

    @Override
    public SuccessDto addAnswer(AnswerNewDto answerNewDto) {
        Optional<Test> test = iTestRepository.findById(answerNewDto.getTest_id());
        Optional<Question> question = iQuestionRepository.findById(answerNewDto.getQuestion_id());
        if(test.isEmpty())
            throw new EntityNotFoundException("Test not found", ErrorCodes.TEST_NOT_FOUND);
        if(question.isEmpty())
            throw new EntityNotFoundException("Question not found", ErrorCodes.QUESTION_NOT_FOUND);
        List<Suggestion> suggestions = new ArrayList<>();
        answerNewDto.getSuggestion_ids().forEach(s -> {
            Optional<Suggestion> suggestion = iSuggestionRepository.findById(s);
            if(suggestion.isEmpty())
                throw new EntityNotFoundException("Suggestion not found", ErrorCodes.SUGGESTION_NOT_FOUND);
            suggestions.add(suggestion.get());
        });
        Answer answer = Answer.builder()
                .answer(suggestions)
                .question(question.get())
                .test(test.get())
                .topic(question.get().getTopic())
                .build();
        iAnswerRepository.save(answer);
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public SuccessDto addMultipleAnswers(AnswersMultipleNewDto dto) {
        Optional<Test> test = iTestRepository.findById(dto.getTestId());
        dto.getAnswers().forEach(multipleAnswers -> {
            Optional<Question> question = iQuestionRepository.findById(multipleAnswers.getQuestionId());
            if(test.isEmpty())
                throw new EntityNotFoundException("Test not found", ErrorCodes.TEST_NOT_FOUND);
            if(question.isEmpty())
                throw new EntityNotFoundException("Question with id : " +multipleAnswers.getQuestionId()+ " not found", ErrorCodes.QUESTION_NOT_FOUND);
            List<Suggestion> suggestions = new ArrayList<>();
            multipleAnswers.getSuggestions().forEach(suggId -> {
                Optional<Suggestion> suggestion = iSuggestionRepository.findById(suggId);
                if(suggestion.isEmpty())
                    throw new EntityNotFoundException("Suggestion with id"+ suggId +"not found", ErrorCodes.SUGGESTION_NOT_FOUND);
                suggestions.add(suggestion.get());
            });
            Answer answer = Answer.builder()
                    .answer(suggestions)
                    .question(question.get())
                    .test(test.get())
                    .topic(question.get().getTopic())
                    .build();
            iAnswerRepository.save(answer);
        });
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }
    @Override
    public AnswerDto getAnswerById(String answerId) {
        Optional<Answer> answer = iAnswerRepository.findById(answerId);
        if(answer.isEmpty())
            throw new EntityNotFoundException("Answer not found", ErrorCodes.ANSWER_NOT_FOUND);
        return AnswerDto.customMapping(answer.get());
    }

    @Override
    public List<AnswerDto> getAllAnswers() {
        return iAnswerRepository.findAll()
                .stream()
                .map(AnswerDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public GetAllAnswersDto getAnswerByTest(String testId) {
        return iAnswerRepository.findAllByTest_Id(testId)
                .stream()
                .map(AnswerDto::customMapping)
                .collect(Collectors.groupingBy(AnswerDto::getTest))
                .entrySet()
                .stream()
                .map(entry -> {
                    TestDto testDto = entry.getKey();
                    List<TopicForAnswerDto> topicForAnswerDto = entry.getValue()
                            .stream()
                            .collect(Collectors.groupingBy(AnswerDto::getTopic))
                            .entrySet()
                            .stream()
                            .map(entry1 -> {
                                TopicDto topic = entry1.getKey();
                                List<QuestionsForAnswerDto> questions = entry1.getValue()
                                        .stream()
                                        .map(QuestionsForAnswerDto::customMapping)
                                        .collect(Collectors.toList());
                                return TopicForAnswerDto.builder()
                                        .topic(topic)
                                        .questions(questions)
                                        .build();
                            })
                            .collect(Collectors.toList());
                    return GetAllAnswersDto.builder()
                            .test(testDto)
                            .topic(topicForAnswerDto)
                            .build();
                })
                .findFirst().get();
    }

}
