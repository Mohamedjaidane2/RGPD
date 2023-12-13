package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.enums.ResultLevel_enum;
import com.example.rgpdplatform.model.Guest;
import com.example.rgpdplatform.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TestExistDto {

    private String id;

    private String ref;

    private int score;

    private ResultLevel_enum resultLevelEnum;

    private Guest guest;

    private List<Topic> topic;

}
