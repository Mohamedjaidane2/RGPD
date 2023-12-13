package com.example.rgpdplatform.service.impl;


import com.example.rgpdplatform.dto.TestDto;
import com.example.rgpdplatform.dto.requests.PaginationDto;
import com.example.rgpdplatform.dto.response.CalculatorDto;
import com.example.rgpdplatform.dto.response.IdDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.TestPaginationResultDto;
import com.example.rgpdplatform.enums.Penalty_status_enum;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.Answer;
import com.example.rgpdplatform.model.Guest;
import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.model.Test;
import com.example.rgpdplatform.repository.IAnswerRepository;
import com.example.rgpdplatform.repository.IGuestRepository;
import com.example.rgpdplatform.repository.ITestRepository;
import com.example.rgpdplatform.service.IAnswerService;
import com.example.rgpdplatform.service.ITestService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.*;

@Service
public class TestServiceImpl implements ITestService {
    private final ITestRepository iTestRepository;
    private final IGuestRepository iGuestRepository;
    private final IAnswerRepository iAnswerRepository;
    private final IAnswerService iAnswerService;

    public TestServiceImpl(ITestRepository iTestRepository, IGuestRepository iGuestRepository, IAnswerRepository iAnswerRepository, IAnswerService iAnswerService) {
        this.iTestRepository = iTestRepository;
        this.iGuestRepository = iGuestRepository;
        this.iAnswerRepository = iAnswerRepository;
        this.iAnswerService = iAnswerService;
    }

    @Override
    public SuccessDto addTest(String email) { //Checked
        Optional<Guest> optionalGuest = iGuestRepository.findGuestByEmail(email);
        if(optionalGuest.isEmpty()){
            throw new EntityNotFoundException("Guest not found!", ErrorCodes.GUEST_NOT_FOUND);
        }
        Test test = Test.builder()
                .guest(optionalGuest.get())
                .refT(generateReference())
                .score(0F)
                .build();
        iTestRepository.save(test);
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public TestDto getTestById(String testId) { //Checked
        if (testId == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        Optional<Test> optionalTest = iTestRepository.findById(testId);
        if (optionalTest.isEmpty()) {
            throw new EntityNotFoundException("Test not found", ErrorCodes.TEST_NOT_FOUND);
        }
        Test test = optionalTest.get();
        return TestDto.customMapping(test);
    }

    @Override
    public Boolean checkById(String testId) {
        if (testId == null) {
            return false;
        }
        Optional<Test> optionalTest = iTestRepository.findById(testId);
        if (optionalTest.isEmpty()) {
            return false;
        }
        return true;
    }
    @Override
    public List<TestDto> getAllTests() { //Checked
        return iTestRepository.findAll()
                .stream()
                .map(TestDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestDto> getTestsByGuest(String email) {
        Optional<Guest> guest = iGuestRepository.findGuestByEmail(email);
        if(guest.isEmpty())
            throw new EntityNotFoundException("Guest not found", ErrorCodes.GUEST_NOT_FOUND);
        return iTestRepository.findByGuest_Id(guest.get().getId())
                .stream()
                .map(TestDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public IdDto getTestByGuest(String email) {
        Optional<Guest> guest = iGuestRepository.findGuestByEmail(email);
        if(guest.isEmpty())
            throw new EntityNotFoundException("Guest not found", ErrorCodes.GUEST_NOT_FOUND);
        List<Test> test = iTestRepository.findByGuest_Id(guest.get().getId());
        if(test.isEmpty()){
            throw new EntityNotFoundException("There are no tests related to this email.", ErrorCodes.TEST_NOT_FOUND);
        }
        test.sort((o1, o2) -> o2.getCreationDate().compareTo(o1.getCreationDate()));
        return IdDto.builder()
                .id(test.get(0).getId())
                .build();
    }

    @Override
    public TestPaginationResultDto getTestsByPagination(PaginationDto paginationDto) {
        List<Test> tests = iTestRepository.findAll();
        TestPaginationResultDto result = TestPaginationResultDto
                .builder()
                .count(tests.size())
                .build();
        result.setTests(tests.stream()
                .skip((long) (paginationDto.getPageNumber() - 1) * paginationDto.getPageSize())
                .limit(paginationDto.getPageSize())
                .map(TestDto::customMapping)
                .collect(Collectors.toList())
        );
        return result;
    }



    @Override
    public SuccessDto deleteTest(String testId) { //Checked
        if (testId == null) {
            throw new IllegalArgumentException("test Id must not be null");
        }
        Optional<Test> testOpt = iTestRepository.findById(testId);
        if (testOpt.isEmpty()) {
            throw new EntityNotFoundException("question not found", ErrorCodes.TEST_NOT_FOUND);
        }
        iTestRepository.deleteById(testId);
        return SuccessDto
                .builder()
                .message(TEST_SUCCESSFULLY_DELETED)
                .build();
    }


    @Override
    public void Calculate(String testId) { //TODO: todo for later
        Optional<Test> test = iTestRepository.findById(testId);
        if(test.isEmpty())
            throw new EntityNotFoundException("Test not found",ErrorCodes.TEST_NOT_FOUND);

        List<Answer> answers = iAnswerRepository.findAllByTest_Id(testId);
        if(answers.isEmpty())
            throw new EntityNotFoundException("There are no answers with this test Id",ErrorCodes.ANSWER_NOT_FOUND);
        CalculatorDto calculator = CalculatorDto.builder()
                .penaltyAmount(0F)
                .penaltyJail(0F)
                .totalCorrectAnswers(0)
                .build();

        answers.forEach(answer ->{
            AtomicBoolean correctAnswer = new AtomicBoolean(true);
            answer.getQuestion().getSuggestions().forEach(QSug ->{
                if(QSug.getGradingScale().getPenaltyStatus() !=  Penalty_status_enum.INDETERMINATE) {
                    Optional<Suggestion> currentSug = answer.getAnswer().stream().filter(suggestion -> { return Objects.equals(QSug.getId(), suggestion.getId()); }).findFirst();
                    if ((currentSug.isEmpty() && QSug.getGradingScale().getPenaltyStatus() == Penalty_status_enum.UNCHECKED) |
                            (currentSug.isPresent() && QSug.getGradingScale().getPenaltyStatus() == Penalty_status_enum.CHECKED)) {
                        correctAnswer.set(false);
                        calculator.setPenaltyJail(calculator.getPenaltyJail() + QSug.getGradingScale().getPenaltyJail());
                        calculator.setPenaltyAmount(calculator.getPenaltyAmount() + QSug.getGradingScale().getPenaltyAmount());
                    }
                }
            });

            if(correctAnswer.get())
                calculator.setTotalCorrectAnswers(calculator.getTotalCorrectAnswers() + 1);
            correctAnswer.set(true);
        });
        float score = ((float) calculator.getTotalCorrectAnswers() / (float) answers.size()) * 100 ;

        Test testToUpdate = test.get();
        testToUpdate.setTotalCorrectAnswers(calculator.getTotalCorrectAnswers());
        testToUpdate.setTotalPenaltyJail(calculator.getPenaltyJail());
        testToUpdate.setTotalPenaltyAmount(calculator.getPenaltyAmount());
        testToUpdate.setScore( score);
        iTestRepository.save(testToUpdate);
    }
    public String generateReference(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }
}

