package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.Guest;
import com.example.rgpdplatform.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    void deleteUserByEmail(String email);

    Boolean existsByEmail(String email);

    User findByResetToken(String resetToken);

    Boolean existsByResetToken(String resetToken);

}
