package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.dto.GuestDto;
import com.example.rgpdplatform.model.Guest;
import com.example.rgpdplatform.model.Organisation;
import com.example.rgpdplatform.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IGuestRepository extends MongoRepository<Guest, String> {
    Optional<Guest> findGuestByEmail(String email);

    List<Guest> findAllByOrganisation(Organisation organisation);

}
