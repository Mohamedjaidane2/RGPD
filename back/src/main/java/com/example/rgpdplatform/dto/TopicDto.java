package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.model.Suggestion;
import com.example.rgpdplatform.model.Topic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TopicDto {

    private String id;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    public static TopicDto customMapping(Topic topic){
        return TopicDto.builder()
                .id(topic.getId())
                .created(topic.getCreationDate())
                .title(topic.getTitle())
                .build();
    }
}
