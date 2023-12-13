package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.dto.requests.update.TopicUpdateDto;
import com.example.rgpdplatform.dto.TopicDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.service.ITopicService;
import com.example.rgpdplatform.utils.SuccessMessages;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Api("/topic")
@RequestMapping(path = "/api/topic")
public class TopicController {
    private final ITopicService iTopicService;

    public TopicController(@Qualifier("topicServiceImpl") ITopicService iTopicService) {
        this.iTopicService = iTopicService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/add/{title}")
    public ResponseEntity<SuccessDto> addTopic(@PathVariable(name = "title") String title) {
        return ResponseEntity.ok(iTopicService.addTopic(title));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/update/details/{title}")
    public ResponseEntity<SuccessDto> updateTopic(@PathVariable(name = "title") String oldTitle, @RequestBody  @Valid TopicUpdateDto topicUpdateDto) {
        return ResponseEntity.ok(iTopicService.updateTopic(oldTitle, topicUpdateDto));
    }


    @GetMapping(path = "/id/{topicId}")
    public ResponseEntity<TopicDto> getTopicById(
            @PathVariable(name = "topicId") String topicId) {
        TopicDto topic = iTopicService.getTopicById(topicId);
        return ResponseEntity.ok(topic);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<TopicDto>> getTopic() {
        List<TopicDto> topics = iTopicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping(path = "/{title}/calculate")
    public ResponseEntity<SuccessDto> calculate(@PathVariable(name = "title") String title) {
        iTopicService.Calculate(title);
        return ResponseEntity.ok(SuccessDto.builder().message(SuccessMessages.SUCCESSFULLY_CAlCULATED).build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{topicId}/deleteById")
    public ResponseEntity<SuccessDto> deleteTopicById(@PathVariable(name = "topicId") String topicId) {
        return ResponseEntity.ok(iTopicService.deleteTopicById(topicId));
    }
}
