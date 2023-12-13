package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.dto.requests.create.GradingScaleNewDto;
import com.example.rgpdplatform.dto.GradingScaleDto;
import com.example.rgpdplatform.dto.response.IdDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.service.IGradingScaleService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("/answer")
@RequestMapping(path = "/api/grading-scale")
public class GradingScaleController {

    private final IGradingScaleService iGradingScaleService;

    public GradingScaleController(IGradingScaleService gradingScaleService) {
        this.iGradingScaleService = gradingScaleService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<IdDto> addGradingScale(@RequestBody @Valid GradingScaleNewDto gradingScaleNewDto) {
        return ResponseEntity.ok(iGradingScaleService.add(gradingScaleNewDto));
    }

    @GetMapping(path = "/{gradingScaleId}")
    public ResponseEntity<GradingScaleDto> getGradingScale(
            @PathVariable(name = "gradingScaleId") String gradingScaleId) {
        GradingScaleDto gradingScale = iGradingScaleService.getGradingScaleById(gradingScaleId);
        return ResponseEntity.ok(gradingScale);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<GradingScaleDto>> getGradingScale() {
        List<GradingScaleDto> gradingScales = iGradingScaleService.getAllGradingScales();
        return ResponseEntity.ok(gradingScales);
    }

    @GetMapping(path = "/bySuggestionId/{suggestionId}")
    public ResponseEntity<GradingScaleDto> getBySuggestion(@PathVariable(name = "suggestionId") String suggestionId) {
        GradingScaleDto gradingScales = iGradingScaleService.getBySuggestion(suggestionId);
        return ResponseEntity.ok(gradingScales);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/update/{gradingScaleId}")
    public ResponseEntity<SuccessDto> updateGradingScale(@PathVariable(name = "gradingScaleId") String gradingScaleId,
                                                         @RequestBody @Valid GradingScaleNewDto gradingScaleNewDto) {
        return ResponseEntity.ok(iGradingScaleService.updateGradingScale(gradingScaleId, gradingScaleNewDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{gradingScaleId}/delete")
    public ResponseEntity<SuccessDto> deleteGradingScaleId(@PathVariable(name = "gradingScaleId") String gradingScaleId) {
        return ResponseEntity.ok(iGradingScaleService.delete(gradingScaleId));
    }
}
