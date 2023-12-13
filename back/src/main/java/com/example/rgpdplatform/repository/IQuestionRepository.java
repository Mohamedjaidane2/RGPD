package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IQuestionRepository extends MongoRepository<Question, String> {
    Optional<Question> findByTitle(String title);
    void deleteByTitle(String title);
    Optional<List<Question>> findAllByTopic_Id(String topicId);

    Optional<Question> findByTopic_Id(String topicId);

    Optional<List<Question>> findAllByIdIn(List<String> questionsId);

    void deleteAllByIdIn(List<String> questionsIds);
}
