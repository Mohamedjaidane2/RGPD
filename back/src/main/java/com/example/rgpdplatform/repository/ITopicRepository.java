package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ITopicRepository extends MongoRepository<Topic, String> {
    Optional<Topic> findByTitle(String title);
    void deleteByTitle(String title);
}
