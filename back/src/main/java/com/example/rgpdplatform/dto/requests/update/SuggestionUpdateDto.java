package com.example.rgpdplatform.dto.requests.update;

import com.example.rgpdplatform.enums.Type_enum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SuggestionUpdateDto {
    private String id;
    private String refS;
    private String title;
    private String gradingScaleId;
}
