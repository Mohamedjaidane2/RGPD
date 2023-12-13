package com.example.rgpdplatform.service.impl;

import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.TopicDto;
import com.example.rgpdplatform.dto.requests.update.TopicUpdateDto;
import com.example.rgpdplatform.dto.response.GetAllQuestionsDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.Question;
import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.model.Topic;
import com.example.rgpdplatform.repository.IGradingScaleRepository;
import com.example.rgpdplatform.repository.IQuestionRepository;
import com.example.rgpdplatform.repository.ISuggestionRepository;
import com.example.rgpdplatform.repository.ITopicRepository;
import com.example.rgpdplatform.service.ITopicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.*;

@Service
public class TopicServiceImpl implements ITopicService {
    private final ITopicRepository iTopicRepository;
    private final IQuestionRepository iQuestionRepository;
    private final ISuggestionRepository iSuggestionRepository;
    private final IGradingScaleRepository iGradingScaleRepository;

    public TopicServiceImpl(ITopicRepository iTopicRepository, IQuestionRepository iQuestionRepository,
                            ISuggestionRepository iSuggestionRepository,
                            IGradingScaleRepository iGradingScaleRepository) {
        this.iTopicRepository = iTopicRepository;
        this.iQuestionRepository = iQuestionRepository;
        this.iSuggestionRepository = iSuggestionRepository;
        this.iGradingScaleRepository = iGradingScaleRepository;
    }

    @Override
    public SuccessDto addTopic(String title) {
            Optional<Topic> optionalTopic = iTopicRepository.findByTitle(title);
            if (optionalTopic.isPresent()) {
                throw new InvalidOperationException("Topic already Exists", ErrorCodes.TOPIC_NOT_FOUND);
            }
            Topic topic =  Topic
                    .builder()
                    .title(title)
                    .build();
            iTopicRepository.save(topic);
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public SuccessDto updateTopic(String oldTitle, TopicUpdateDto topicUpdateDto) {
        Optional<Topic> optionalTopic = iTopicRepository.findByTitle(oldTitle) ;
        if(optionalTopic.isEmpty()){
            throw new EntityNotFoundException("topic not found with title: "+ oldTitle, ErrorCodes.TOPIC_NOT_FOUND);
        }
       // List<Question> questions = iQuestionRepository.findAllByIdIn(topicUpdateDto.getQuestionsId());
        //Topic topicToUpdate = optionalTopic.get();
        //questions.forEach(question -> {
          //  question.setTopic(optionalTopic.get());
          //  iQuestionRepository.save(question);
        //});
        Topic topicToUpdate=optionalTopic.get();
        topicToUpdate.setTitle(topicUpdateDto.getTitle());
        iTopicRepository.save(topicToUpdate);

        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_UPDATED)
                .build();
    }


    @Override
    public TopicDto getTopicById(String topicId) {
        Optional<Topic> optionalTopic = iTopicRepository.findById(topicId);
        if (optionalTopic.isEmpty()) {
            throw new EntityNotFoundException("Topic not found", ErrorCodes.TOPIC_NOT_FOUND);
        }
        Optional<GetAllQuestionsDto> questionDto = iQuestionRepository.findByTopic_Id(topicId)
                .stream()
                .map(QuestionDto::customMapping)
                .collect(Collectors.groupingBy(QuestionDto::getTopic))
                .entrySet()
                .stream()
                .map(entry -> {
                    TopicDto topicDto = entry.getKey();
                    List<QuestionDto> questionsDto = entry.getValue()
                            .stream()
                            .peek(p-> p.setTopic(null))
                            .collect(Collectors.toList());
                    return GetAllQuestionsDto.builder()
                            .questions(questionsDto)
                            .topic(topicDto)
                            .build();
                }).findFirst();
        if (optionalTopic.isEmpty()) {
            throw new InvalidOperationException("Something went wrong!", ErrorCodes.TOPIC_NOT_VALID);
        }
        Topic topic = optionalTopic.get();

        return TopicDto.customMapping(topic);
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return iTopicRepository.findAll()
                .stream()
                .map(TopicDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public void Calculate(String topic_title) {
        /*Optional<Topic> optionalTopic = iTopicRepository.findByTitle(topic_title);
        if (optionalTopic.isEmpty()){
            throw new EntityNotFoundException("Topic not found with Title : " + topic_title, ErrorCodes.TOPIC_NOT_FOUND);
        }
        AtomicInteger penalty_jail_sum = new AtomicInteger(0);
        AtomicInteger penalty_amount_sum = new AtomicInteger(0);

        Topic topic= optionalTopic.get();

        topic.getQuestions().forEach(question -> {
            question.getSuggestions().forEach(suggestion -> {
                //penalty_amount_sum.getAndAdd(suggestion.getPenalty_amount());
                //penalty_jail_sum.getAndAdd(suggestion.getPenalty_jail());
            });
        });
        //topic.setPenalty_jail(penalty_jail_sum.get());
        //topic.setPenalty_amount(penalty_amount_sum.get());
        iTopicRepository.save(topic);*/
    }

    @Override
    public SuccessDto deleteTopicById(String topicId) {
        Optional<Topic> topicOpt = iTopicRepository.findById((topicId));
        if(topicOpt.isEmpty())
            throw new EntityNotFoundException("topic not found", ErrorCodes.TOPIC_NOT_FOUND);
        Optional<List<Question>> questions = iQuestionRepository.findAllByTopic_Id(topicId);

        questions.ifPresent(questionList -> {
            questionList.forEach(question -> {
                question.getSuggestions().forEach(suggestion -> {
                    String gradingScaleId = suggestion.getGradingScale().getId();
                    iGradingScaleRepository.deleteById(gradingScaleId);
                });
                iSuggestionRepository.deleteAllByIdIn(question.getSuggestions()
                        .stream()
                        .map(Suggestion::getId)
                        .collect(Collectors.toList()));
                });
                iQuestionRepository.deleteAllByIdIn(questionList
                    .stream()
                    .map(Question::getId)
                    .collect(Collectors.toList()));
            });
        iTopicRepository.deleteById(topicId);
        return SuccessDto
                .builder()
                .message(TOPIC_SUCCESSFULLY_DELETED)
                .build();
    }


}



