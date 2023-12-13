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
public class SubQuestionNewDto {
    private String refQ;

    private String title;

    private List<String> suggestionSrc;

    private Type_enum type;

}
