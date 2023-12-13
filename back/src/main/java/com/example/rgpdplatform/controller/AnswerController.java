package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.dto.AnswerDto;
import com.example.rgpdplatform.dto.requests.create.AnswerNewDto;
import com.example.rgpdplatform.dto.requests.create.AnswersMultipleNewDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.answerResult.GetAllAnswersDto;
import com.example.rgpdplatform.service.IAnswerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("/answer")
@RequestMapping(path = "/api/answer")
public class AnswerController {

    private final IAnswerService iAnswerService;

    public AnswerController(@Qualifier("answerServiceImpl") IAnswerService iAnswerService) {
        this.iAnswerService = iAnswerService;
    }
    @PostMapping(path = "/add")
    public ResponseEntity<SuccessDto> addAnswer(@RequestBody @Valid AnswerNewDto answerNewDto) {
        return ResponseEntity.ok(iAnswerService.addAnswer(answerNewDto));
    }
    @PostMapping(path = "/addMultiple")
    public ResponseEntity<SuccessDto> addMultipleAnswers(@RequestBody @Valid AnswersMultipleNewDto dto) {
        return ResponseEntity.ok(iAnswerService.addMultipleAnswers(dto));
    }
    @GetMapping(path = "/{answerId}")
    public ResponseEntity<AnswerDto> getAnswer(
            @PathVariable(name = "answerId") String answerId) {
        AnswerDto answer = iAnswerService.getAnswerById(answerId);
        return ResponseEntity.ok(answer);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<AnswerDto>> getAnswers() {
        List<AnswerDto> answers = iAnswerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @GetMapping(path = "/byTest/{testId}")
    public ResponseEntity<GetAllAnswersDto> getAnswerByTest(@PathVariable(name = "testId") String guestId) {
        GetAllAnswersDto answers = iAnswerService.getAnswerByTest(guestId);
        return ResponseEntity.ok(answers);
    }

}
