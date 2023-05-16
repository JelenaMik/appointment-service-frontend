package com.example.security.repository;

import java.util.List;
import java.util.Optional;

import com.example.security.auth.AuthenticationRequest;
import com.example.security.auth.AuthenticationResponse;
import com.example.security.auth.RegisterRequest;
import com.example.security.enums.Role;

import com.example.security.repository.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
@RequiredArgsConstructor
@Repository
@Log4j2
public class UserRepository {

    private static final String token= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNUBtYWlsLmNvbSIsImlhdCI6MTY4NDE1Mzg4MSwiZXhwIjoxNjg0Mjk3ODgxfQ.YeCybJzp0feaA97MI811uy6BKCQjt9L6HNL7JmYku2I";
    HttpEntity<Object> entity =  new HttpEntity<>(authenticationHeader(this.token));
    public HttpHeaders authenticationHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ token);
        return headers;
    }


    private static final String USER_SERVER_BASE_URL="http://localhost:8101/api/v1/userservice/";

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    public Optional<User> findById(Long id){
        return Optional.ofNullable( restTemplate.exchange(USER_SERVER_BASE_URL + id, HttpMethod.GET, this.entity, User.class).getBody());
    }

    public List <User> findFirst10ByRole(Role role){
        return restTemplate.exchange(USER_SERVER_BASE_URL + role, HttpMethod.GET, this.entity, List.class).getBody();
    }

    public Long findIdByEmail(String email){
        return restTemplate.exchange(USER_SERVER_BASE_URL + "find-id/"+email, HttpMethod.GET, this.entity, Long.class).getBody();
    }

    public Optional<User> findByEmail(String email){
        return Optional.ofNullable(restTemplate.exchange(USER_SERVER_BASE_URL + "find-user/"+email, HttpMethod.GET, this.entity, User.class).getBody());
    }
    public String getUserRoleById(Long userId) {
        return restTemplate.exchange(USER_SERVER_BASE_URL + "find-role/"+userId, HttpMethod.GET, this.entity, String.class).getBody();
    }
    //NO AUTH required
    public Boolean existsByEmail(String email){
        return restTemplate.exchange(USER_SERVER_BASE_URL + "exists/"+email, HttpMethod.GET, this.entity, Boolean.class).getBody();
    }


    //No auth needed
    public AuthenticationResponse getTokenAfterAuthentication(AuthenticationRequest request) {
        HttpEntity<Object> entity1 =  new HttpEntity<>(request);
        AuthenticationResponse response = restTemplate.exchange("http://localhost:8101/api/v1/auth/authenticate", HttpMethod.POST, entity1, AuthenticationResponse.class).getBody();
//         jwtToken.setToken(response.getToken());
        return response;
    }

    //No auth needed
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        HttpEntity<Object> entity1 =  new HttpEntity<>(registerRequest);
        AuthenticationResponse response = restTemplate.exchange("http://localhost:8101/api/v1/auth/register", HttpMethod.POST, entity1, AuthenticationResponse.class).getBody();
//        this.token = response.getToken();
        return response;
    }

    public void changeUserRole(Long id, String role) {
        restTemplate.exchange(USER_SERVER_BASE_URL + "change-role/"+id+"/"+role, HttpMethod.GET, this.entity, ResponseEntity.class);
    }

    public AuthenticationResponse changeEmailAndPassword(AuthenticationRequest user, String oldEmail) {
        HttpEntity<Object> entity1 =  new HttpEntity<>(user, authenticationHeader(this.token));
        return restTemplate.exchange(USER_SERVER_BASE_URL + "change/"+oldEmail, HttpMethod.POST, entity1, AuthenticationResponse.class).getBody();
    }

    public List<User> findUsersBySearching(String email) {
        List<User> list = restTemplate.exchange(USER_SERVER_BASE_URL + "users?email="+email, HttpMethod.GET, this.entity, List.class).getBody();
        return mapper.convertValue(list, new TypeReference<List<User>>() {});
    }

    public String adminChangePassword(Long userId) {
        return restTemplate.exchange(USER_SERVER_BASE_URL + "admin-change-password/"+userId, HttpMethod.POST, this.entity, String.class).getBody();
    }

    public Boolean isUserClient(Long clientId) {
        return restTemplate.exchange(USER_SERVER_BASE_URL + "is-client/"+clientId, HttpMethod.GET, this.entity, Boolean.class).getBody();
    }

    public Boolean isUserProvider(Long providerId) {
        return restTemplate.exchange(USER_SERVER_BASE_URL + "if-provider/"+providerId, HttpMethod.GET, this.entity, Boolean.class).getBody();
    }
}
