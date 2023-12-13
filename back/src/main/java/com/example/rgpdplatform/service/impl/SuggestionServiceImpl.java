package com.example.rgpdplatform.service.impl;

import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.requests.create.SuggestionNewDto;
import com.example.rgpdplatform.dto.requests.update.SuggestionUpdateDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.SuggestionRefDto;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.GradingScale;
import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.model.Question;
import com.example.rgpdplatform.model.Topic;
import com.example.rgpdplatform.repository.IGradingScaleRepository;
import com.example.rgpdplatform.repository.ISuggestionRepository;
import com.example.rgpdplatform.repository.IQuestionRepository;
import com.example.rgpdplatform.repository.ITopicRepository;
import com.example.rgpdplatform.service.ISuggestionService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.*;

@Service
public class SuggestionServiceImpl implements ISuggestionService {

    private final ISuggestionRepository suggestionRepository;

    private final IQuestionRepository iQuestionRepository;
    private final IGradingScaleRepository iGradingScaleRepository;
    private final MongoTemplate mongoTemplate;
    private final ITopicRepository iTopicRepository;

    public SuggestionServiceImpl(ISuggestionRepository suggestionRepository, IQuestionRepository iQuestionRepository, IGradingScaleRepository iGradingScaleRepository, MongoTemplate mongoTemplate,
                                 ITopicRepository iTopicRepository) {
        this.suggestionRepository = suggestionRepository;
        this.iQuestionRepository = iQuestionRepository;
        this.iGradingScaleRepository = iGradingScaleRepository;
        this.mongoTemplate = mongoTemplate;
        this.iTopicRepository = iTopicRepository;
    }

    @Override
    public SuccessDto addSuggestion(SuggestionNewDto suggestionNewDto) {
        try {
            Optional<Question> optionalQuestion = iQuestionRepository.findById(suggestionNewDto.getQuestion_id());
            if (optionalQuestion.isEmpty()) {
                throw new EntityNotFoundException("Question not found", ErrorCodes.QUESTION_NOT_FOUND);
            }
            Optional<Suggestion> optionalSuggestion = suggestionRepository.findByRefS(suggestionNewDto.getRef());
            if (optionalSuggestion.isPresent()) {
                throw new EntityNotFoundException("Suggestion already Exists", ErrorCodes.SUGGESTION_IN_USE);
            }
            Optional<GradingScale> gradingScale = iGradingScaleRepository.findById(suggestionNewDto.getGradingScale_id());
            if(gradingScale.isEmpty()) //FIXME
                throw new EntityNotFoundException("Grading Scale not found", ErrorCodes.GRADING_SCALE_NOT_FOUND);
            Question question = optionalQuestion.get();
            Suggestion suggestion = Suggestion
                    .builder()
                    .title(suggestionNewDto.getTitle())
                    .refS(suggestionNewDto.getRef())
                    .gradingScale(gradingScale.get())
                    .build();

            suggestionRepository.save(suggestion);
            question.getSuggestions().add(suggestion);
            iQuestionRepository.save(question);
        } catch (InvalidOperationException invalidOperationException) {
            throw new InvalidOperationException("Suggestion Not Valid !", ErrorCodes.SUGGESTION_NOT_VALID);
        }
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public SuccessDto updateSuggestion(SuggestionUpdateDto suggestionDto) {
        Optional<Suggestion> optionalSuggestion = suggestionRepository.findById(suggestionDto.getId()) ;
        if(optionalSuggestion.isEmpty()){
            throw new EntityNotFoundException("Suggestion not found with id : "+ suggestionDto.getRefS(), ErrorCodes.SUGGESTION_NOT_FOUND);
        }

        Optional<GradingScale> optionalGradingScale = iGradingScaleRepository.findById(suggestionDto.getGradingScaleId()) ;
        if(optionalGradingScale.isEmpty()){
            throw new EntityNotFoundException("Grading Scale not found with id : "+ suggestionDto.getGradingScaleId(), ErrorCodes.GRADING_SCALE_NOT_FOUND);
        }

        Suggestion suggestion = optionalSuggestion.get();

        suggestion.setRefS(suggestionDto.getRefS());
        suggestion.setTitle(suggestionDto.getTitle());
        suggestion.setGradingScale(optionalGradingScale.get());
        suggestionRepository.save(suggestion);
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_UPDATED)
                .build();
    }


    @Override
    public SuggestionDto getSuggestionById(String SuggestionId) {
        if (SuggestionId == null) {
            throw new IllegalArgumentException("SuggestionId must not be null");
        }

        Optional<Suggestion> suggestionIdOpt = suggestionRepository.findById(SuggestionId);
        if (suggestionIdOpt.isEmpty()) {
            throw new EntityNotFoundException("Suggestion not found", ErrorCodes.SUGGESTION_NOT_FOUND);
        }
        return SuggestionDto.customMapping(suggestionIdOpt.get());
    }

    @Override
    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }

    @Override
    public SuccessDto deleteSuggestionByRef(String suggestionRef) {
        if (suggestionRef == null) {
            throw new IllegalArgumentException("suggestion Ref must not be null");
        }
        Optional<Suggestion> SuggestionOpt = suggestionRepository.findByRefS(suggestionRef);
        if (SuggestionOpt.isEmpty()) {
            throw new EntityNotFoundException("Suggestion not found", ErrorCodes.SUGGESTION_NOT_FOUND);
        }
        suggestionRepository.deleteByRefS(suggestionRef);
        return SuccessDto
                .builder()
                .message(SUGGESTION_SUCCESSFULLY_DELETED)
                .build();
    }

    @Override
    public SuccessDto deleteSuggestionById(String suggestionId) {
        if (suggestionId == null) {
            throw new IllegalArgumentException("suggestion Id must not be null");
        }
        Optional<Suggestion> SuggestionOpt = suggestionRepository.findById(suggestionId);
        if (SuggestionOpt.isEmpty()) {
            throw new EntityNotFoundException("Suggestion not found", ErrorCodes.SUGGESTION_NOT_FOUND);
        }
        String  gradingScale = SuggestionOpt.get().getGradingScale().getId();
        iGradingScaleRepository.deleteById(gradingScale);
        suggestionRepository.deleteById(suggestionId);
        return SuccessDto
                .builder()
                .message(SUGGESTION_SUCCESSFULLY_DELETED)
                .build();
    }

    @Override
    public List<SuggestionRefDto> getSuggestionNamesByTopicId(String topicId) {

        Optional<Topic> topic = iTopicRepository.findById(topicId);
        if (topic.isEmpty()) {
            throw new EntityNotFoundException("Topic not found", ErrorCodes.TOPIC_NOT_FOUND);
        }
        List<SuggestionRefDto> suggestionRefDtos = new ArrayList<>();
        List<Question> questions = iQuestionRepository.findAllByTopic_Id(topicId).get();
        questions.forEach(question -> {
            question.getSuggestions().forEach(suggest -> {
                suggestionRefDtos.add(new SuggestionRefDto(suggest.getId(),suggest.getRefS()));
            });
        });
        return suggestionRefDtos;
    }

}



