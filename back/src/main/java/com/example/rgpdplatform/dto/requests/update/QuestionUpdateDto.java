package com.example.rgpdplatform.dto.requests.update;

import com.example.rgpdplatform.dto.TopicDto;
import com.example.rgpdplatform.enums.Type_enum;
import com.example.rgpdplatform.model.Suggestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuestionUpdateDto {

    private String id;

    private String title;

    private Type_enum type;

    private String refQ;

    private String topicId;

    private List<String> suggestionIds;

    private List<String> suggestionSrcIds;


}
