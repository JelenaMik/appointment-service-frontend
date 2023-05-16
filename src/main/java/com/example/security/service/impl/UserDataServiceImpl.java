package com.example.security.service.impl;

import com.example.security.exceptions.UserDataNotFoundException;
import com.example.security.model.UserData;
import com.example.security.repository.UserDataRepository;
import com.example.security.service.UserDataService;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {

    private final UserDataRepository userDataRepository;
    private final UserService userService;

    @Override
    public List<UserData> getProviderListIfSearchingStringHasText(String string){
        return userDataRepository.getProviderListIfSearchingStringHasText(string);
    }
    @Override
    public List<UserData> getProviderListIfSearchingStringIsEmpty() {
        return userDataRepository.getProviderListIfSearchingStringIsEmpty();
    }

    @Override
    public UserData getUserDataByUserId(Long userId){
        return userDataRepository.getUserDataByUserId(userId).orElseThrow(UserDataNotFoundException::new);
    }

    @Override
    public void saveUserData(UserData userData){
        userDataRepository.saveUserData(userData);
        log.info("UserData was saved {}", userData);
    }

    @Override
    public void updateUserData(UserData userData){
        userDataRepository.updateUserData(userData);
    }

    @Override
    public List<UserData> getProvidersData(List<Long> first10Providers) {
        return first10Providers.stream()
                .map(providerId -> userDataRepository.getUserDataByUserId(providerId).get())
                .toList();
    }

    @Override
    public List<UserData> getAllUsersData(){
        return userDataRepository.getAllUsersData();
    }

    @Override
    public void addFavoriteProvider(Long clientId, Long providerId) {
        if(userService.isUserClient(clientId) && userService.isUserProvider(providerId))
            userDataRepository.addFavoriteProvider(clientId, providerId);
        log.info("Client with id {} added favorite provider with id {}", clientId, providerId);
    }

    @Override
    public List<UserData> getFavoriteProviders(Long clientId) {
        List<UserData> list = userDataRepository.getFavoriteProviders(clientId);
        log.info("Client with Id: {} favorite provider List: {} ", clientId, list);
        return list;
    }

    @Override
    public void deleteFavoriteProvider(Long clientId, Long providerId) {
        userDataRepository.deleteFavoriteProvider(clientId, providerId);
        log.info("Provider with Id {} was removed from client Id {} favorite providers list", clientId, providerId);
    }

}
