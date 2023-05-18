package com.example.security.service.impl;

import com.example.security.repository.JwtRepository;
import com.example.security.repository.model.Jwt;
import com.example.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    public final JwtRepository repository;
    @Override
    public Optional<Jwt> getToken() {
        return repository.getFirstByTokenIsNotNull();
    }

    @Override
    public void saveToken(String token){
        repository.save(Jwt.builder().id(1L).token(token).build());
    }

    @Override
    public void removeToken(){
        repository.deleteAll();
    }

//    HttpEntity<Object> entity =  new HttpEntity<>(authenticationHeader(getToken().get().getToken()));
//    @Override
//    public HttpHeaders authenticationHeader(String token){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer "+ token);
//        return headers;
//    }

    @Override
    public HttpEntity<Object> httpEntityWithAuthHeaderAndRequestBody(Object body){
        HttpHeaders headers = new HttpHeaders();
        if(getToken().isPresent()) {
            headers.add("Authorization", "Bearer "+ getToken().get().getToken());
        }
        HttpEntity<Object> entity =  new HttpEntity<>(body, headers);
        return entity;
    }

    @Override
    public HttpEntity<Object> httpEntityWithAuthHeader(){
        HttpHeaders headers = new HttpHeaders();
        if(getToken().isPresent()) {
            headers.add("Authorization", "Bearer "+ getToken().get().getToken());
        }
        HttpEntity<Object> entity =  new HttpEntity<>(headers);
        return entity;
    }
}
