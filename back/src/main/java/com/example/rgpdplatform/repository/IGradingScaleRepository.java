package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.GradingScale;
import com.example.rgpdplatform.model.Suggestion;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGradingScaleRepository extends MongoRepository<GradingScale, String> {

    Optional<GradingScale> findByTitle(String title);


    void delete(@NotNull GradingScale gradingScale);
}
