package com.example.rgpdplatform.dto.requests.create;

import com.example.rgpdplatform.enums.Type_enum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuestionNewDto {

    private String refQ;

    private String title;

    private Type_enum type;

    private String topic_id;

    private List<String> suggestionSrc ;

}
