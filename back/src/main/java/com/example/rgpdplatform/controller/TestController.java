package com.example.rgpdplatform.controller;


import com.example.rgpdplatform.dto.TestDto;
import com.example.rgpdplatform.dto.requests.PaginationDto;
import com.example.rgpdplatform.dto.response.IdDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.TestPaginationResultDto;
import com.example.rgpdplatform.service.ITestService;
import com.example.rgpdplatform.utils.SuccessMessages;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api("/test")
@RequestMapping(path = "/api/test")
public class TestController {
    @Qualifier("testServiceImpl")
    @Autowired
    private ITestService iTestService;

    @PostMapping(path = "/add/{email}")
    public ResponseEntity<SuccessDto> addTest(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(iTestService.addTest(email));
    }

    @GetMapping(path = "/{testId}")
    public ResponseEntity<TestDto> getTestById(@PathVariable(name = "testId") String testId) {
        TestDto test = iTestService.getTestById(testId);
        return ResponseEntity.ok(test);
    }

    @GetMapping(path = "checkById/{testId}")
    public ResponseEntity<Boolean> checkById(@PathVariable(name = "testId") String testId) {
        return ResponseEntity.ok(iTestService.checkById(testId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<TestDto>> getTests() {
        List<TestDto> tests = iTestService.getAllTests();
        return ResponseEntity.ok(tests);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/all/pagination")
    public ResponseEntity<TestPaginationResultDto> getTestsByPagination(@RequestBody PaginationDto paginationDto) {
        return ResponseEntity.ok(iTestService.getTestsByPagination(paginationDto));
    }

    @GetMapping(path = "/allByGuestEmail/{email}")
    public ResponseEntity<List<TestDto>> allByGuestEmail(@PathVariable(name = "email") String email) {
        List<TestDto> tests = iTestService.getTestsByGuest(email);
        return ResponseEntity.ok(tests);
    }
    @GetMapping(path = "/guestEmail/{email}")
    public ResponseEntity<IdDto> getTestsByGuest(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(iTestService.getTestByGuest(email));
    }


    @DeleteMapping(path = "/{testId}/delete")
    public ResponseEntity<SuccessDto> deleteTest(@PathVariable(name = "testId") String testId) {
        return ResponseEntity.ok(iTestService.deleteTest(testId));
    }


    @GetMapping(path = "/{testId}/calculate")
    public ResponseEntity<SuccessDto> calculate(@PathVariable(name = "testId") String testId) {
        iTestService.Calculate(testId);
        return ResponseEntity.ok(SuccessDto.builder()
                        .message(SuccessMessages.SUCCESSFULLY_CAlCULATED)
                        .build());
    }
}
