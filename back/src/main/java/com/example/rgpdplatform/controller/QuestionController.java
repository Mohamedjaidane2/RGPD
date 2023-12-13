package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.dto.requests.create.SubQuestionNewDto;
import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.QuestionDto;
import com.example.rgpdplatform.dto.requests.create.QuestionNewDto;
import com.example.rgpdplatform.dto.requests.update.QuestionUpdateDto;
import com.example.rgpdplatform.dto.response.GetAllQuestionsDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.model.Question;
import com.example.rgpdplatform.service.IQuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Api("/question")
@RequestMapping(path = "/api/question")
public class QuestionController {
    private final IQuestionService iQuestionService;

    public QuestionController(@Qualifier("questionServiceImpl") IQuestionService iQuestionService) {
        this.iQuestionService = iQuestionService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/addQuestion")
    public ResponseEntity<SuccessDto> addQuestion(@RequestBody @Valid QuestionNewDto questionNewDto) {
        return ResponseEntity.ok(iQuestionService.addQuestion(questionNewDto));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/addSubQuestions/{questionId}")
    public ResponseEntity<SuccessDto> addSubQuestions(@PathVariable(name = "questionId") String questionId,
                                                      @RequestBody @Valid List<SubQuestionNewDto> subQuestionNewDto) {
        return ResponseEntity.ok(iQuestionService.addSubQuestions(questionId, subQuestionNewDto));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/update")
    public ResponseEntity<SuccessDto> updateQuestion(@RequestBody @Valid QuestionUpdateDto questionDto) {
        return ResponseEntity.ok(iQuestionService.updateQuestion(questionDto));
    }

    @GetMapping(path = "/title/{questionTitle}")
    public ResponseEntity<QuestionDto> getQuestion(
            @PathVariable(name = "questionTitle") String questionTitle) {
        QuestionDto question = iQuestionService.getQuestionByTitle(questionTitle);
        return ResponseEntity.ok(question);
    }

    @GetMapping(path = "/id/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(
            @PathVariable(name = "questionId") String questionId) {
        QuestionDto question = iQuestionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<GetAllQuestionsDto>> getQuestion() {
        List<GetAllQuestionsDto> questions = iQuestionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping(path = "/allRef")
    public ResponseEntity<List<QuestionDto>> getQuestionsRef() {
        List<QuestionDto> questions = iQuestionService.getAllQuestionRef();
        return ResponseEntity.ok(questions);
    }

    @GetMapping(path = "/getSuggestions/{questionTitle}")
    public ResponseEntity<List<SuggestionDto>> getSuggestionsByQuestionTitle(@PathVariable(name = "questionTitle") String questionTitle) {
            List<SuggestionDto> suggestions = iQuestionService.getSuggestionListByQuestion(questionTitle);
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping(path = "/getSubQuestions/{questionId}")
    public ResponseEntity<List<QuestionDto>> getSubQuestionsByQuestionId(@PathVariable(name = "questionId") String questionId) {
        List<QuestionDto> questions = iQuestionService.getSubQuestionListByQuestionId(questionId);
        return ResponseEntity.ok(questions);
    }


    @GetMapping(path = "/byTopic/{topicId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByTopic(
            @PathVariable(name = "topicId") String topicId) {
        List<QuestionDto> questionsDto = iQuestionService.getQuestionByTopic(topicId);
        return ResponseEntity.ok(questionsDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{questionId}/deleteById")
    public ResponseEntity<SuccessDto> deleteById(@PathVariable(name = "questionId") String questionId) {
        return ResponseEntity.ok(iQuestionService.deleteById(questionId));
    }
}
