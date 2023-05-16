package com.example.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

//    private final RestTemplate restTemplate;

//    private final AuthenticationService service;
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request
//    ) {
//        return ResponseEntity.ok(service.register(request));
//    }
//    @PostMapping("/authenticate")
//
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }
//
//
//    @GetMapping("/registration")
//    public ResponseEntity<String> showInfo(){
//        return ResponseEntity.ok("You need to register");
//    }

//    @PostMapping("save_user_details/{id}")
//    public ResponseEntity<UserData> saveUserData(@PathVariable Long id, String firstName, String lastName){
//
//    UserData userData = new UserData();
//    userData.setLastName(lastName);
//    userData.setUserId(id);
//    userData.setFirstName(firstName);
//
//        UserData userDataSaved = restTemplate.postForObject("http://localhost:8102/api/v1/userdata/save", userData,  UserData.class);
//
//        return ResponseEntity.ok(userDataSaved);
//}

}