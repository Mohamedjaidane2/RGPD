package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.TestDto;
import com.example.rgpdplatform.dto.requests.PaginationDto;
import com.example.rgpdplatform.dto.response.IdDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.TestPaginationResultDto;

import java.util.List;

public interface ITestService {

    SuccessDto addTest(String email);


    SuccessDto deleteTest(String testId);

    TestDto getTestById(String testId);

    List<TestDto> getAllTests();

    void Calculate (String testId);

    List<TestDto> getTestsByGuest(String email);

    IdDto getTestByGuest(String email);

    TestPaginationResultDto getTestsByPagination(PaginationDto paginationDto);

    Boolean checkById(String testId);
}
