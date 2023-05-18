package com.example.security.service;

import com.example.security.model.UserData;

import java.util.List;

public interface UserDataService {
    List<UserData> getProviderListIfSearchingStringHasText(String string);

    List<UserData> getProviderListIfSearchingStringIsEmpty() ;

    UserData getUserDataByUserId(Long userId);

    void saveUserData(UserData userData);

    void updateUserData(UserData userData);

    List<UserData> getProvidersData(List<Long> first10Providers);

    List<UserData> getAllUsersData();
    void addFavoriteProvider(Long clientId, Long providerId);

    List<UserData> getFavoriteProviders(Long clientId);

    void deleteFavoriteProvider(Long clientId, Long providerId);

    void authenticateUserDataMs();
}
