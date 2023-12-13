package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.Suggestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ISuggestionRepository extends MongoRepository<Suggestion, String> {
    Optional<Suggestion> findByRefS(String ref);

    Optional<List<Suggestion>> findAllByGradingScale_Id(String gradingScaleId);

    void deleteByRefS(String ref);

    Optional<List<Suggestion>> findAllByIdIn(List<String> suggestionsIds);

    void deleteAllByIdIn(List<String> suggestionsIds);
}
