package com.example.rgpdplatform.service.impl;



import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.TopicDto;
import com.example.rgpdplatform.dto.requests.create.QuestionNewDto;
import com.example.rgpdplatform.dto.requests.create.SubQuestionNewDto;
import com.example.rgpdplatform.dto.requests.update.QuestionUpdateDto;
import com.example.rgpdplatform.dto.response.GetAllQuestionsDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.model.Question;
import com.example.rgpdplatform.model.Topic;
import com.example.rgpdplatform.repository.IGradingScaleRepository;
import com.example.rgpdplatform.repository.IQuestionRepository;
import com.example.rgpdplatform.repository.ISuggestionRepository;
import com.example.rgpdplatform.repository.ITopicRepository;
import com.example.rgpdplatform.service.IQuestionService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.*;



@Service
public class QuestionServiceImpl implements IQuestionService {
    private final IQuestionRepository iQuestionRepository;
    private final ITopicRepository iTopicRepository;
    private final ISuggestionRepository iSuggestionRepository;
    private final IGradingScaleRepository iGradingScaleRepository;

    public QuestionServiceImpl(IQuestionRepository iQuestionRepository, ITopicRepository iTopicRepository, ISuggestionRepository iSuggestionRepository,
                               IGradingScaleRepository iGradingScaleRepository) {
        this.iQuestionRepository = iQuestionRepository;
        this.iTopicRepository = iTopicRepository;
        this.iSuggestionRepository = iSuggestionRepository;
        this.iGradingScaleRepository = iGradingScaleRepository;
    }

    @Override
    public SuccessDto addQuestion(QuestionNewDto questionNewDto) { //checked
        Optional<Topic> optionalTopic=iTopicRepository.findById(questionNewDto.getTopic_id());
        if (optionalTopic.isEmpty()) {
            throw new EntityNotFoundException("Topic Not Found ", ErrorCodes.TOPIC_NOT_FOUND);
        }

        Optional<Question> checkQuestion = iQuestionRepository.findByTitle(questionNewDto.getTitle());
        //System.out.println("the size of the list is : " + question.size());
        if (checkQuestion.isPresent()) {
            throw new EntityNotFoundException("Question Already in use ", ErrorCodes.QUESTION_IN_USE);
        }
        Question questionToAdd = Question.builder()
                .refQ(questionNewDto.getRefQ())
                .type(questionNewDto.getType())
                .title(questionNewDto.getTitle())
                .topic(optionalTopic.get())
                .build();

        if(questionNewDto.getSuggestionSrc() != null) {
            Optional<List<Suggestion>> optionalSuggestionSrc = iSuggestionRepository.findAllByIdIn(questionNewDto.getSuggestionSrc());
            if (optionalSuggestionSrc.isEmpty()) {
                throw new EntityNotFoundException("Suggestion Source Not Found ", ErrorCodes.SUGGESTION_NOT_FOUND);
            }
            questionToAdd.setSuggestionSrc(optionalSuggestionSrc.get());
        }

        iQuestionRepository.save(questionToAdd);
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }


    @Override
    public SuccessDto updateQuestion(QuestionUpdateDto questionDto) {
        Optional<Question> optionalQuestion = iQuestionRepository.findById(questionDto.getId());
        if(optionalQuestion.isEmpty()){
            throw new EntityNotFoundException("question not found with id : "+questionDto.getId(), ErrorCodes.QUESTION_NOT_FOUND);
        }
        Question question = optionalQuestion.get();

        Optional<Topic> optionalTopic = iTopicRepository.findById(questionDto.getTopicId());
        if (optionalTopic.isEmpty()) {
            throw new EntityNotFoundException("Topic not found", ErrorCodes.TOPIC_NOT_FOUND);
        }
        Optional<List<Suggestion>> suggestions = Optional.empty();
        if(!questionDto.getSuggestionIds().isEmpty()){
            suggestions = iSuggestionRepository.findAllByIdIn(questionDto.getSuggestionIds());
            if (suggestions.isEmpty())
                throw new EntityNotFoundException("suggestions not found", ErrorCodes.QUESTION_NOT_FOUND);
        }
        Optional<List<Suggestion>> suggestionSrc = Optional.empty();
        if (!questionDto.getSuggestionSrcIds().isEmpty()){
            suggestionSrc = iSuggestionRepository.findAllByIdIn(questionDto.getSuggestionSrcIds());

            if (suggestionSrc.isEmpty())
                throw new EntityNotFoundException("suggestion source not found", ErrorCodes.QUESTION_NOT_FOUND);
            question.setSuggestionSrc(suggestionSrc.get());

        }
        question.setTopic(optionalTopic.get());
        question.setTitle(questionDto.getTitle());
        question.setType(questionDto.getType());
        question.setRefQ(questionDto.getRefQ());
        question.setSuggestions(suggestions.get());
        iQuestionRepository.save(question);

        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_UPDATED)
                .build();
    }


    @Override
    public QuestionDto getQuestionByTitle(String questionTitle) {
        Optional<Question> optionalQuestion = iQuestionRepository.findByTitle(questionTitle);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            return QuestionDto.customMapping(question);
        } else {
            throw new EntityNotFoundException("question not found with Title : " + questionTitle, ErrorCodes.QUESTION_NOT_FOUND);
        }
    }

    @Override
    public QuestionDto getQuestionById(String questionId) {
        Optional<Question> optionalQuestion = iQuestionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            return QuestionDto.customMapping(question);
        } else {
            throw new EntityNotFoundException("question not found with question Id : " + questionId, ErrorCodes.QUESTION_NOT_FOUND);
        }
    }

    @Override
    public List<QuestionDto> getQuestionByTopic(String topicId) {
        Optional<List<Question>> optionalQuestions = iQuestionRepository.findAllByTopic_Id(topicId);
        if (optionalQuestions.isEmpty()){
            throw new EntityNotFoundException("Topic not found with topicId : " + topicId, ErrorCodes.TOPIC_NOT_FOUND);
        }
        return QuestionDto.customListMapping(optionalQuestions.get());
    }


    @Override
    public List<GetAllQuestionsDto> getAllQuestions() {
        return iQuestionRepository.findAll()
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
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllQuestionRef() {
        return iQuestionRepository.findAll()
                .stream()
                .map(QuestionDto::smallCustomMapping)
                .collect(Collectors.toList());
    }
    @Override
    public List<SuggestionDto> getSuggestionListByQuestion(String title) {
        Optional<Question> questionOptional = iQuestionRepository.findByTitle(title);
        if (questionOptional.isEmpty()){
            throw new EntityNotFoundException("question not found with Title : " + title, ErrorCodes.QUESTION_NOT_FOUND);
        }
        List<Suggestion> suggestions = questionOptional.get().getSuggestions();
        List<SuggestionDto> suggestionDtoArrayList = new ArrayList<>();
        suggestions.forEach(suggestion-> {
            SuggestionDto suggestionDto = SuggestionDto.builder()
                    .id(suggestion.getId())
                    //.ref(suggestion.getRef())
                    //.penalty_jail(suggestion.getPenalty_jail())
                    //.penalty_amount(suggestion.getPenalty_amount())
                    .created(suggestion.getCreationDate())
                    .title(suggestion.getTitle())
                    .title(title)
                    .build();
            suggestionDtoArrayList.add(suggestionDto);
        });
        return suggestionDtoArrayList;

    }

    @Override
    public List<QuestionDto> getSubQuestionListByQuestionId(String questionId) {
        return iQuestionRepository.findById(questionId).get().getSubQuestions()
                .stream()
                .map(QuestionDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessDto addSubQuestions(String questionId, List<SubQuestionNewDto> subQuestions) {
        Optional<Question> bossQuestion = iQuestionRepository.findById(questionId);
        if (bossQuestion.isEmpty()){
            throw new EntityNotFoundException("question not found with question Id : " + questionId, ErrorCodes.QUESTION_NOT_FOUND);
        }
        List<Question> subQuestionsToAdd =  bossQuestion.get().getSubQuestions();
        subQuestions.forEach(QuestionDto -> {

            Optional<Question> checkQuestion = iQuestionRepository.findByTitle(QuestionDto.getTitle());
            if (checkQuestion.isPresent()) {
                throw new EntityNotFoundException("Question Already in use!", ErrorCodes.QUESTION_IN_USE);
            }

            Optional<List<Suggestion>> suggestion = iSuggestionRepository.findAllByIdIn(QuestionDto.getSuggestionSrc());
            if(suggestion.isEmpty()){
                throw new EntityNotFoundException("Suggestion not found!", ErrorCodes.SUGGESTION_NOT_FOUND);
            }

            Question subQuestion = iQuestionRepository.save(
                    Question.builder()
                            .type(QuestionDto.getType())
                            .refQ(QuestionDto.getRefQ())
                            .title(QuestionDto.getTitle())
                            .topic(bossQuestion.get().getTopic())
                            .suggestionSrc(suggestion.get())
                            .build()
            );
            subQuestionsToAdd.add(subQuestion);

        });
        bossQuestion.get().setSubQuestions(subQuestionsToAdd);
        iQuestionRepository.save(bossQuestion.get());
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public SuccessDto deleteById(String questionId) {
        Optional<Question> optionalQuestion = iQuestionRepository.findById(questionId);
        if(optionalQuestion.isEmpty()){
            throw new EntityNotFoundException("question not found with id : "+questionId, ErrorCodes.QUESTION_NOT_FOUND);
        }
        Question question = optionalQuestion.get();
        if(!question.getSubQuestions().isEmpty()) {
            iQuestionRepository.deleteAllByIdIn(question.getSubQuestions()
                    .stream()
                    .map(Question::getId)
                    .collect(Collectors.toList())
            );
        }
        if(!question.getSuggestions().isEmpty()){
            question.getSuggestions().forEach(suggestion -> iGradingScaleRepository.deleteById(suggestion.getGradingScale().getId()));
            iSuggestionRepository.deleteAllByIdIn(question.getSuggestions()
                    .stream()
                    .map(Suggestion::getId)
                    .collect(Collectors.toList()));
        }
        iQuestionRepository.deleteById(question.getId());
        return SuccessDto
                .builder()
                .message(QUESTION_SUCCESSFULLY_DELETED)
                .build();
    }
}