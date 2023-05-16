//package com.example.security.restControllersSpringBootTests;
//
//import com.example.security.auth.AuthenticationRequest;
//import com.example.security.auth.RegisterRequest;
//import com.example.security.enums.Role;
//import com.example.security.repository.UserRepository;
//import com.example.security.repository.model.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.validation.ConstraintViolationException;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@MockBean(UserRepository.class)
//public class AuthenticationControllerTests {
//
//    @Autowired
//    private UserRepository repository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @SneakyThrows
//    void testAuthentication(){
//
//        User user = User.builder()
//                .email("user@mail.com")
//                .password("$2a$10$cUJP2xCZchVc2hhRhrwwcO8MdKY0OBCm2tgYwSe1nmJPljkNxfzd.")
//                .role(Role.USER)
//                .build();
//
//        AuthenticationRequest request = AuthenticationRequest.builder()
//                .email("user@mail.com")
//                .password("1111")
//                .build();
//
//        when(repository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(request)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
//                .andReturn();
//
//    }
//
//    @Test
//    @SneakyThrows
//    void testAuthenticationWrongPassword(){
//
//        User user = User.builder()
//                .email("user@mail.com")
//                .password("$2a$10$cUJP2xCZchVc2hhRhrwwcO8MdKY0OBCm2tgYwSe1nmJPljkNxfzd.")
//                .role(Role.USER)
//                .build();
//
//        AuthenticationRequest request = AuthenticationRequest.builder()
//                .email("user@mail.com")
//                .password("11")
//                .build();
//
//        when(repository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(user));
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(request)))
//                .andDo(print())
//                .andExpect(status().is(403))
//                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void testAuthenticationWrongEmail(){
//
//        AuthenticationRequest request = AuthenticationRequest.builder()
//                .email("user@mail.com")
//                .password("1111")
//                .build();
//
//        when(repository.findByEmail("user@mail.com")).thenReturn(Optional.ofNullable(null));
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(request)))
//                .andDo(print())
//                .andExpect(status().is(403))
//                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
//                .andReturn();
//    }
//
//
//
//    public static String asJsonString(AuthenticationRequest request) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(request);
//        } catch(Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @SneakyThrows
//    void testRegistration(){
//
//        User user = User.builder()
//                .email("user@mail.com")
//                .password("$2a$10$cUJP2xCZchVc2hhRhrwwcO8MdKY0OBCm2tgYwSe1nmJPljkNxfzd.")
//                .role(Role.USER)
//                .build();
//        User userWithId = User.builder()
//                .id(1L)
//                .email("user@mail.com")
//                .password("$2a$10$cUJP2xCZchVc2hhRhrwwcO8MdKY0OBCm2tgYwSe1nmJPljkNxfzd.")
//                .role(Role.USER)
//                .build();
//
//        RegisterRequest request = RegisterRequest.builder()
//                .email("user@mail.com")
//                .password("1111")
//                .build();
//
//        when(repository.save(user)).thenReturn(userWithId);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/auth/register")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(registerAsJsonString(request)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
//                .andReturn();
//
//    }
//
//
//    public static String registerAsJsonString(RegisterRequest request) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(request);
//        } catch(Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
