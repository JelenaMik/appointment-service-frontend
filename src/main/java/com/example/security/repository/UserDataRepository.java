package com.example.security.repository;

import com.example.security.model.UserData;
import com.example.security.service.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Log4j2
public class UserDataRepository {

    private final JwtService jwtService;

    private static final String USER_DATA_SERVER_BASE_URL="http://localhost:8102/api/v1/userdata/";
    private static final String USER_SERVER_BASE_URL="http://localhost:8101/api/v1/userservice/";
    private static final String USER_DATA_FAVORITE_URL="http://localhost:8102/api/v1/favorite/";

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    public List<UserData> searchUserDataByName(String string){
          List<UserData > list = restTemplate.exchange(
                  USER_DATA_SERVER_BASE_URL+"search-users?firstName=" + string,
                  HttpMethod.GET,
                  jwtService.httpEntityWithAuthHeader(),
                  List.class).getBody();
        return  mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }

    public List<UserData> getProvidersData(List<Long> firstProviders){
        return restTemplate.exchange(
                "http://localhost:8102/api/v1/userdata/provider-data",
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeaderAndRequestBody(firstProviders),
                List.class )
                .getBody();
    }

    public Optional<UserData> getUserDataByUserId(Long userId){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer "+ "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNUBtYWlsLmNvbSIsImlhdCI6MTY4Mzg4NjE2NywiZXhwIjoxNjgzODg3NjA3fQ.EWV-lx9ujKBXurDd9RnjsswabFHRsK8vEvBue1aLRfk");
        return Optional.ofNullable(restTemplate.exchange(
                USER_DATA_SERVER_BASE_URL+"get-data/"+userId,
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                UserData.class).getBody());
    }


    public void saveUserData(UserData userData){
         restTemplate.exchange(
                 USER_DATA_SERVER_BASE_URL+"save",
                 HttpMethod.POST,
                 jwtService.httpEntityWithAuthHeaderAndRequestBody(userData),
                 UserData.class);
    }

    public void updateUserData(UserData userData){
        restTemplate.exchange(
                USER_DATA_SERVER_BASE_URL+"update-user-data",
                HttpMethod.PUT,
                jwtService.httpEntityWithAuthHeaderAndRequestBody(userData),
                ResponseEntity.class);
    }

    //Check if works without mapper
    public List<UserData> getAllUsersData(){
        return restTemplate.exchange(
                USER_DATA_SERVER_BASE_URL+"all-users-data",
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                List.class).getBody();
    }

    public void addFavoriteProvider(Long clientId, Long providerId){
        restTemplate.exchange(
                USER_DATA_FAVORITE_URL+"add?clientId=" +clientId+"&providerId="+providerId,
                HttpMethod.POST,
                jwtService.httpEntityWithAuthHeader(),
                ResponseEntity.class);
    }

    public List<UserData> getFavoriteProviders(Long clientId) {
        List<UserData> list = restTemplate.exchange(
                USER_DATA_FAVORITE_URL+"providers/"+clientId,
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                List.class).getBody();
        return mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }

    public void deleteFavoriteProvider(Long clientId, Long providerId) {
        restTemplate.exchange(
                USER_DATA_FAVORITE_URL+"remove?clientId=" + clientId + "&providerId="+providerId,
                HttpMethod.DELETE,
                jwtService.httpEntityWithAuthHeader(),
                ResponseEntity.class);
    }

    public List<UserData> getProviderListIfSearchingStringHasText(String string) {
        List<UserData> list = restTemplate.exchange(
                USER_SERVER_BASE_URL+"search-providers/" + string+"?token="+jwtService.getToken().get().getToken(),
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                List.class).getBody();
        return mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }

    public List<UserData> getProviderListIfSearchingStringIsEmpty() {
        List<UserData> list = restTemplate.exchange(
                USER_SERVER_BASE_URL+"search-providers",
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                List.class).getBody();
        return mapper.convertValue(list, new TypeReference<List<UserData>>() {});
    }

    public void authenticate() {
        restTemplate.exchange(
                "http://localhost:8102/authenticate",
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                HttpStatus.class
        ).getBody();
    }
}
