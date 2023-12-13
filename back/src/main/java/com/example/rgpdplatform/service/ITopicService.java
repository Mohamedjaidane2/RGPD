package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.TopicDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.requests.update.*;
import java.util.List;

public interface ITopicService {

    SuccessDto addTopic(String title);

    SuccessDto updateTopic(String oldTitle, TopicUpdateDto topicUpdateDto);

    List<TopicDto> getAllTopics();

    void Calculate (String topic_title);

    TopicDto getTopicById(String topicId);

    SuccessDto deleteTopicById(String topicId);
}
