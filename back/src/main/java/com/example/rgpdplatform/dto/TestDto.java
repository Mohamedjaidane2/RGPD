package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TestDto {

    private String id;

    private String refT;

    private GuestDto guest;

    private Float score;

    private Float penaltyJail;

    private Float penaltyAmount;

    private Integer totalCorrectAnswers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    public static TestDto customMapping(Test test){
        if( test.getGuest() == null ) throw new EntityNotFoundException("Guest not found", ErrorCodes.GUEST_NOT_FOUND);
        return TestDto.builder()
                .id(test.getId())
                .refT(test.getRefT())
                .guest(GuestDto.customMapping(test.getGuest()))
                .created(test.getCreationDate())
                .score(test.getScore())
                .penaltyAmount(test.getTotalPenaltyAmount())
                .penaltyJail(test.getTotalPenaltyJail())
                .totalCorrectAnswers(test.getTotalCorrectAnswers())
                .build();
    }

    public static List<TestDto> customListMapping(List<Test> tests){
        ArrayList<TestDto> testsDto = new ArrayList<>();
        for (Test test : tests) {
            TestDto testDto = customMapping(test);
            testsDto.add(testDto);
        }
        return testsDto;
    }
}
