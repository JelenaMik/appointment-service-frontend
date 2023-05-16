//package com.example.security.restControllersSpringBootTests;
//
//import com.example.security.enums.Role;
//import com.example.security.exceptions.UserDataNotFoundException;
//import com.example.security.exceptions.UserIsNotClientException;
//import com.example.security.exceptions.UserIsNotProviderException;
//import com.example.security.exceptions.UserNotFoundException;
//import com.example.security.repository.UserDataRepository;
//import com.example.security.repository.UserRepository;
//import com.example.security.repository.model.User;
//import com.example.security.model.UserData;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@MockBeans({
//        @MockBean(UserDataRepository.class),
//        @MockBean(UserRepository.class)
//        })
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserControllerSpringBootTest {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserDataRepository userDataRepository;
//    @Autowired
//    private MockMvc mockMvc;
//
//    UserData userDataEntityNoId = new UserData();
//    UserData userDataEntity = new UserData();
//    UserData userDataEntity2 = new UserData();
//    UserData userDataEntity3 = new UserData();
//    UserData userDataEntityUpdated = new UserData();
//    User user1 = new User();
//    User user2 = new User();
//    User user3 = new User();
//    User user4 = new User();
//
//    @BeforeEach
//    void setUp(){
//
//        userDataEntityNoId = UserData.builder()
//                .userId(1L)
//                .firstName("Anna")
//                .lastName("Liepina")
//                .build();
//
//        userDataEntity = UserData.builder()
//                .id(10L)
//                .userId(1L)
//                .firstName("Anna")
//                .lastName("Liepina")
//                .build();
//
//        userDataEntity2 = UserData.builder()
//                .id(11L)
//                .userId(2L)
//                .firstName("Haralds")
//                .lastName("Ozols")
//                .build();
//
//        userDataEntity3 = UserData.builder()
//                .id(10L)
//                .userId(3L)
//                .firstName("Eva")
//                .lastName("Adams")
//                .build();
//
//        userDataEntityUpdated = UserData.builder()
//                .id(10L)
//                .userId(1L)
//                .firstName("Anna")
//                .lastName("Kalnina")
//                .build();
//        user1 = User.builder()
//                .id(1L)
//                .email("user1@mail.com")
//                .password("$2a$10$90m2gguI/d0vqbPmVxTXveZH8JTmf.XndsWz1aDVS87t.IYCiJGz6")
//                .role(Role.PROVIDER)
//                .build();
//        user2 = User.builder()
//                .id(1L)
//                .email("user1@mail.com")
//                .password("$2a$10$SLGnS0kJhsBdA6Wv6LweruyZrxH1cAIs/Itkrk7dU78K8wPxg5kMi")
//                .role(Role.PROVIDER)
//                .build();
//        user3 = User.builder()
//                .id(3L)
//                .email("user3@mail.com")
//                .password("$2a$10$90m2gguI/d0vqbPmVxTXveZH8JTmf.XndsWz1aDVS87t.IYCiJGz6")
//                .role(Role.PROVIDER)
//                .build();
//        user4 = User.builder()
//                .id(4L)
//                .email("user4@mail.com")
//                .password("$2a$10$90m2gguI/d0vqbPmVxTXveZH8JTmf.XndsWz1aDVS87t.IYCiJGz6")
//                .role(Role.CLIENT)
//                .build();
//
//
//    }
//
//
//
//    @Test
//    @SneakyThrows
//    void testAllUSerData(){
//        when(userDataRepository.getAllUsersData()).thenReturn(List.of(userDataEntity, userDataEntity2, userDataEntity3));
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/all-user-data")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
//                .andReturn();
//
//    }
//    @Test
//    @SneakyThrows
//    void testAllUSerDataEmptyList(){
//        when(userDataRepository.getAllUsersData()).thenReturn(List.of());
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/all-user-data")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
//                .andReturn();
//    }
//    @Test
//    @SneakyThrows
//    void getUserByIdSuccess(){
//        when(userDataRepository.getUserDataByUserId(1L)).thenReturn(Optional.of(userDataEntity));
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/user-data/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Liepina"))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void getUserByIdNoFount(){
//        when(userDataRepository.getUserDataByUserId(1L)).thenReturn(Optional.ofNullable(null));
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/user-data/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Last Name"))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void updateUserData(){
//        doNothing().when(userDataRepository).updateUserData(userDataEntityUpdated);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/v1/user/update-user-data")
//                                .content(asJsonString(userDataEntityUpdated))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    public static String asJsonString(UserData userData) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(userData);
//        } catch(Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @SneakyThrows
//    void updateUserDataException(){
//        doThrow(UserDataNotFoundException.class).when(userDataRepository).updateUserData(userDataEntityUpdated);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/v1/user/update-user-data")
//                                .content(asJsonString(userDataEntityUpdated))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(res -> assertTrue(res.getResolvedException() instanceof UserDataNotFoundException))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void changePasswordSuccess(){
//        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user1));
//        when(userRepository.save(user2)).thenReturn(user2);
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/v1/user/change-password/1")
//                                .queryParam("password", "6666")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void changePasswordUserNotFound(){
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/v1/user/change-password/1")
//                                .queryParam("password", "6666")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(res -> assertTrue(res.getResolvedException() instanceof UserNotFoundException))
//                .andReturn();
//    }
//    @Test
//    @SneakyThrows
//    void changeEmailSuccess(){
//        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user1));
//        when(userRepository.save(user3)).thenReturn(user3);
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/v1/user/change-email/1")
//                                .queryParam("email", "user3@mail.com")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void changeEmailUserNotFound(){
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/v1/user/change-email/1")
//                                .queryParam("email", "user3@mail.com")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(res -> assertTrue(res.getResolvedException() instanceof UserNotFoundException))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void getFirst10ProvidersSuccess(){
//        when(userRepository.findFirst10ByRole(Role.PROVIDER)).thenReturn(List.of(user1, user3));
//        when(userDataRepository.getUserDataByUserId(1L)).thenReturn(Optional.of(userDataEntity));
//        when(userDataRepository.getUserDataByUserId(3L)).thenReturn(Optional.of(userDataEntity3));
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/get-providers")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void getFirst10ProvidersEmptyList(){
//        when(userRepository.findFirst10ByRole(Role.PROVIDER)).thenReturn(List.of());
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/get-providers")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void addFavoriteProviderSuccess(){
//        when(userRepository.existsByRoleAndId(Role.PROVIDER, 1L)).thenReturn(true);
//        when(userRepository.existsByRoleAndId(Role.CLIENT, 2L)).thenReturn(true);
//        doNothing().when(userDataRepository).addFavoriteProvider(2L,1L);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/user/add-favorite")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .queryParam("clientId", "2")
//                                .queryParam("providerId", "1")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void addFavoriteProviderWrongClientRole(){
//        when(userRepository.existsByRoleAndId(Role.PROVIDER, 1L)).thenReturn(true);
//        when(userRepository.existsByRoleAndId(Role.CLIENT, 2L)).thenReturn(false);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/user/add-favorite")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .queryParam("clientId", "2")
//                                .queryParam("providerId", "1")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(res -> assertTrue(res.getResolvedException() instanceof UserIsNotClientException))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void addFavoriteProviderWrongProviderRole(){
//        when(userRepository.existsByRoleAndId(Role.PROVIDER, 1L)).thenReturn(false);
//        when(userRepository.existsByRoleAndId(Role.CLIENT, 2L)).thenReturn(true);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/user/add-favorite")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .queryParam("clientId", "2")
//                                .queryParam("providerId", "1")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(res -> assertTrue(res.getResolvedException() instanceof UserIsNotProviderException))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void addFavoriteProvidersListSuccess(){
//        when(userDataRepository.getFavoriteProviders(any())).thenReturn(List.of(userDataEntity, userDataEntity3));
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/favorite-providers/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void addFavoriteProvidersListEmpty(){
//        when(userDataRepository.getFavoriteProviders(any())).thenReturn(List.of());
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/v1/user/favorite-providers/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
//                .andReturn();
//    }
//
//    @Test
//    @SneakyThrows
//    void deleteFavoriteProvider(){
//        doNothing().when(userDataRepository).deleteFavoriteProvider(1L,2L);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.delete("/api/v1/user/delete-favorite")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isNoContent())
//                .andReturn();
//    }
//
//
//
//
//
//
//}
