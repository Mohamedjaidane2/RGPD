package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnswerRepository extends MongoRepository<Answer, String> {
    List<Answer> findAllByTest_Id(String testId);
}
