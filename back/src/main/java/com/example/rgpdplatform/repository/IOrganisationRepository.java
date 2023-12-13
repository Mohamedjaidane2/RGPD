package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.Organisation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrganisationRepository extends MongoRepository<Organisation, String> {
        Optional<Organisation> findByName (String name);
        void deleteByName(String name);
}
