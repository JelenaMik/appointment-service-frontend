package com.example.security.repository;

import com.example.security.repository.model.Jwt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRepository extends MongoRepository<Jwt, Long> {
    Optional<Jwt> getFirstByTokenIsNotNull();
}
