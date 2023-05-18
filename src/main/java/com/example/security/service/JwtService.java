package com.example.security.service;

import com.example.security.repository.model.Jwt;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface JwtService {
    //should be only one JWT in DB
    Optional<Jwt> getToken();

//    HttpHeaders authenticationHeader(String token);

    void saveToken(String token);

    void removeToken();

    HttpEntity<Object> httpEntityWithAuthHeaderAndRequestBody(Object body);

    HttpEntity<Object> httpEntityWithAuthHeader();
}
