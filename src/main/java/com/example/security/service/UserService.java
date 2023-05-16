package com.example.security.service;

import com.example.security.auth.AuthenticationRequest;
import com.example.security.auth.AuthenticationResponse;
import com.example.security.auth.RegisterRequest;
import com.example.security.repository.model.User;


import java.util.List;

public interface UserService {
//    public AuthenticationResponse changePassword(Long userId, String password);
//    public AuthenticationResponse changeEmail(Long userId, String email);

    AuthenticationResponse changeEmailAndPassword(AuthenticationRequest user, String oldEmail);

    Long getUserIdByEmail(String email);

    void changeUserRole(Long id, String role);

    User getUserById(Long id);

    List<User> findUsersBySearching(String email);


    List<Long> findProvidersBySearching();

    Boolean isUserDataProvider(Long userId);

    Boolean isUserProvider(Long userId);

    Boolean isUserClient(Long clientId);

    String adminChangePassword(Long userId);

    String getUserRoleById(Long userId);

    String getTokenAfterAuthentication(AuthenticationRequest request);
    Boolean checkIfEmailExists(String email);

    AuthenticationResponse register(RegisterRequest registerRequest);
}
