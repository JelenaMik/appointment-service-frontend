//package com.example.security.web;
//
//import com.example.security.auth.AuthenticationRequest;
//import com.example.security.auth.AuthenticationResponse;
//import com.example.security.auth.AuthenticationService;
//import com.example.security.model.UserData;
//import com.example.security.service.UserDataService;
//import com.example.security.service.UserService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
////@Api(tags = "User login Controller", description = "Operations performing on registration and login")
//@RestController
//@Log4j2
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/user")
//public class UserController {
//
//    private final UserService userService;
//    private final AuthenticationService service;
//    private final UserDataService userDataService;
//
//    //    Testing
//    @GetMapping("/hi")
//    public ResponseEntity<String> sayHi() {
//        return ResponseEntity.ok("Hi from secured!");
//    }
//
//    @GetMapping("/all-user-data")
//    public ResponseEntity<List<UserData>> allUsersData() {
//        List<UserData> list = userDataService.getAllUsersData();
//        return ResponseEntity.ok(list);
//    }
//
//    @GetMapping("/user-data/{id}")
//    @CircuitBreaker(
//            name = "user-data-service", fallbackMethod = "hardCodedUserData"
//    )
//    public ResponseEntity<UserData> userData(@PathVariable Long id) {
//        UserData userData = userDataService.getUserDataByUserId(id);
//        return ResponseEntity.ok(userData);
//    }
//
//    public ResponseEntity<UserData> hardCodedUserData(Long id, Exception e) {
//        UserData userData =
//                new UserData(null, id, "First Name", "Last Name", LocalDateTime.now());
//        return ResponseEntity.ok(userData);
//    }
//
//
//    @PutMapping("/update-user-data")
//    public ResponseEntity<HttpStatus> updateData(@RequestBody UserData userData) {
//        userDataService.updateUserData(userData);
////        restTemplate.put(USER_SERVICE+"update-user-data", userData);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @PutMapping("/change-password/{id}")
//    public ResponseEntity<AuthenticationResponse> changePassword(@PathVariable Long id, String password) {
//        return ResponseEntity.ok(userService.changePassword(id, password));
//    }
//
//    @PutMapping("/change-email/{id}")
//    public ResponseEntity<AuthenticationResponse> changeEmail(@PathVariable Long id, String email) {
//        return ResponseEntity.ok(userService.changeEmail(id, email));
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }
//
//    @GetMapping("/get-providers")
//    public ResponseEntity<List> getProviderList() {
//        return ResponseEntity.ok(userDataService.getProviderListIfSearchingStringIsEmpty());
//    }
//
//    @PostMapping("/add-favorite")
//    public ResponseEntity<HttpStatus> addFavoriteProvider(Long clientId, Long providerId){
//        log.info("Client id: {}", clientId);
//        userDataService.addFavoriteProvider(clientId, providerId);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @GetMapping("/favorite-providers/{clientId}")
//    public ResponseEntity<List> getFavoriteProvidersIds(@PathVariable Long clientId){
//        return new ResponseEntity<>(userDataService.getFavoriteProviders(clientId), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete-favorite")
//    public ResponseEntity<HttpStatus> removeFavoriteProvider(Long clientId, Long providerId){
//        userDataService.deleteFavoriteProvider(clientId, providerId);
//        return ResponseEntity.noContent().build();
//    }
//
//
//
//}
