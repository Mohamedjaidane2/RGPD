package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.dto.SuggestionDto;
import com.example.rgpdplatform.dto.requests.create.SuggestionNewDto;
import com.example.rgpdplatform.dto.requests.update.SuggestionUpdateDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.SuggestionRefDto;
import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.service.ISuggestionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("/suggestion")
@RequestMapping(path = "/api/suggestion")
public class SuggestionController {
    private final ISuggestionService iSuggestionService;

    public SuggestionController(@Qualifier("suggestionServiceImpl") ISuggestionService iSuggestionService) {
        this.iSuggestionService = iSuggestionService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Add new Suggestion")
    @PostMapping(path = "/add")
    public ResponseEntity<SuccessDto> addSuggestion(@RequestBody @Valid SuggestionNewDto suggestionNewDto) {
        return ResponseEntity.ok(iSuggestionService.addSuggestion(suggestionNewDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update Suggestion")
    @PatchMapping(path = "/update")
    public ResponseEntity<SuccessDto> updateSuggestion(@RequestBody @Valid SuggestionUpdateDto suggestionDto) {
        return ResponseEntity.ok(iSuggestionService.updateSuggestion(suggestionDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get Suggestions by topic Id")
    @GetMapping(path = "/getByTopic/{topicId}")
    public ResponseEntity<List<SuggestionRefDto>> getSuggestionRefsByTopicId(@PathVariable(name = "topicId") String topicId) {
        return ResponseEntity.ok(iSuggestionService.getSuggestionNamesByTopicId(topicId));
    }

    @ApiOperation(value = "Get Suggestion by Id")
    @GetMapping(path = "/{suggestionId}")
    public ResponseEntity<SuggestionDto> getSuggestion(
            @PathVariable(name = "suggestionId") String suggestionId) {
        SuggestionDto suggestion = iSuggestionService.getSuggestionById(suggestionId);
        return ResponseEntity.ok(suggestion);
    }

    @ApiOperation(value = "Get all suggestions")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Suggestion>> getAllSuggestions(){
        List<Suggestion> suggestions = iSuggestionService.getAllSuggestions();

        return ResponseEntity.ok(suggestions);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete Suggestion by ref")
    @DeleteMapping(path = "/{suggestionRef}/deleteByRef")
    public ResponseEntity<SuccessDto> deleteSuggestionByRef(@PathVariable(name = "suggestionRef") String suggestionRef) {
        return ResponseEntity.ok(iSuggestionService.deleteSuggestionByRef(suggestionRef));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete Suggestion by Id")
    @DeleteMapping(path = "/{suggestionId}/deleteById")
    public ResponseEntity<SuccessDto> deleteSuggestionById(@PathVariable(name = "suggestionId") String suggestionId) {
        return ResponseEntity.ok(iSuggestionService.deleteSuggestionById(suggestionId));
    }
}
