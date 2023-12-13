package com.example.rgpdplatform.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class SuggestionNewDto {

    private String title;

    private String ref;

    private String question_id;

    private String gradingScale_id;

}
