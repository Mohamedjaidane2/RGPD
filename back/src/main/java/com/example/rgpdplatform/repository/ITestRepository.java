package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.Guest;
import com.example.rgpdplatform.model.Test;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITestRepository extends MongoRepository<Test, String> {


    Test save(Test test);

    Optional<Test> findTestByRefT(String ref);

    void deleteByRefT(String ref);

    List<Test> findByGuest_Id(String guestId);
}
