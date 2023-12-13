package com.example.rgpdplatform.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TopicUpdateDto {

    private String title;

    private List<String> questionsId;

}
