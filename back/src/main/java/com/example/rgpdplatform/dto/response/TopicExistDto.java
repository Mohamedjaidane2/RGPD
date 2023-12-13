package com.example.rgpdplatform.dto.response;
import com.example.rgpdplatform.model.Question;
import com.example.rgpdplatform.model.Test;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class TopicExistDto {

    private String id;

    private String title;

    private Integer score;

    private Integer penalty_jail;

    private int penalty_amount;

    private List<Test> test;

    private List<Question> questions ;

}
