package com.example.security.repository;

import com.example.security.model.UserData;
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

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Log4j2
public class UserDataRepository {

    private static final String token= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNUBtYWlsLmNvbSIsImlhdCI6MTY4NDE1Mzg4MSwiZXhwIjoxNjg0Mjk3ODgxfQ.YeCybJzp0feaA97MI811uy6BKCQjt9L6HNL7JmYku2I";
    HttpEntity<Object> entity =  new HttpEntity<>(authenticationHeader(this.token));

    public HttpHeaders authenticationHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ token);
        return headers;
    }

    private static final String USER_DATA_SERVER_BASE_URL="http://localhost:8102/api/v1/userdata/";
    private static final String USER_SERVER_BASE_URL="http://localhost:8101/api/v1/userservice/";
    private static final String USER_DATA_FAVORITE_URL="http://localhost:8102/api/v1/favorite/";

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    public List<UserData> searchUserDataByName(String string){
          List<UserData > list = restTemplate.exchange(USER_DATA_SERVER_BASE_URL+"search-users?firstName=" + string, HttpMethod.GET, this.entity, List.class).getBody();
        return  mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }

    public List<UserData> getProvidersData(List<Long> firstProviders){
        HttpEntity<Object> entity1 =  new HttpEntity<>(firstProviders, authenticationHeader(this.token));
        return restTemplate.exchange(
                "http://localhost:8102/api/v1/userdata/provider-data",
                HttpMethod.GET, entity1,
                List.class )
                .getBody();
    }

    public Optional<UserData> getUserDataByUserId(Long userId){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer "+ "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNUBtYWlsLmNvbSIsImlhdCI6MTY4Mzg4NjE2NywiZXhwIjoxNjgzODg3NjA3fQ.EWV-lx9ujKBXurDd9RnjsswabFHRsK8vEvBue1aLRfk");
        return Optional.ofNullable(restTemplate.exchange(USER_DATA_SERVER_BASE_URL+"get-data/"+userId, HttpMethod.GET, this.entity,  UserData.class).getBody());
    }


    public void saveUserData(UserData userData){
        HttpEntity<Object> entity1 =  new HttpEntity<>(userData, authenticationHeader(this.token));
         restTemplate.exchange(USER_DATA_SERVER_BASE_URL+"save", HttpMethod.POST, entity1,  UserData.class);
    }

    public void updateUserData(UserData userData){
        HttpEntity<Object> entity1 =  new HttpEntity<>(userData, authenticationHeader(this.token));
        restTemplate.exchange(USER_DATA_SERVER_BASE_URL+"update-user-data", HttpMethod.PUT, entity1, ResponseEntity.class);
    }

    //Check if works without mapper
    public List<UserData> getAllUsersData(){
        return restTemplate.exchange(USER_DATA_SERVER_BASE_URL+"all-users-data", HttpMethod.GET, this.entity, List.class).getBody();
    }

    public void addFavoriteProvider(Long clientId, Long providerId){
        restTemplate.exchange(USER_DATA_FAVORITE_URL+"add?clientId=" +clientId+"&providerId="+providerId, HttpMethod.POST, this.entity, ResponseEntity.class);
    }

    public List<UserData> getFavoriteProviders(Long clientId) {
        List<UserData> list = restTemplate.exchange(USER_DATA_FAVORITE_URL+"providers/"+clientId, HttpMethod.GET, this.entity, List.class).getBody();
        return mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }

    public void deleteFavoriteProvider(Long clientId, Long providerId) {
        restTemplate.exchange(USER_DATA_FAVORITE_URL+"remove?clientId=" + clientId + "&providerId="+providerId, HttpMethod.DELETE, this.entity, ResponseEntity.class);
    }

    public List<UserData> getProviderListIfSearchingStringHasText(String string) {
        List<UserData> list = restTemplate.exchange(USER_SERVER_BASE_URL+"search-providers/" + string+"?token="+this.token, HttpMethod.GET, this.entity, List.class).getBody();
        return mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }

    public List<UserData> getProviderListIfSearchingStringIsEmpty() {
        List<UserData> list = restTemplate.exchange(USER_SERVER_BASE_URL+"search-providers", HttpMethod.GET, this.entity, List.class).getBody();
        return mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }
}
