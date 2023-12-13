package com.example.rgpdplatform.repository;

import com.example.rgpdplatform.enums.Role_enum;
import com.example.rgpdplatform.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IRoleRepository  extends MongoRepository<Role, String> {

    Optional<Role> findByRoleName(String roleName);

}
