package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRefreshTokenRepository extends MongoRepository<RefreshToken, String> {
}
