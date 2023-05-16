//package com.example.security.serviceSpringBootTests;
//
//import com.example.security.enums.Role;
//import com.example.security.exceptions.UserNotFoundException;
//import com.example.security.repository.UserRepository;
//import com.example.security.repository.model.User;
//import com.example.security.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@MockBeans({
//        @MockBean( UserRepository.class)
//})
//public class UserServiceTests {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    User user3 = new User();
//    User user4 = new User();
//    User user5 = new User();
//
//    @BeforeEach
//    void setUp(){
//        user3 = User.builder()
//                .id(3L)
//                .email("user3@mail.com")
//                .password("$2a$10$90m2gguI/d0vqbPmVxTXveZH8JTmf.XndsWz1aDVS87t.IYCiJGz6") //1111
//                .role(Role.PROVIDER)
//                .build();
//        user4 = User.builder()
//                .id(3L)
//                .email("user3@mail.com")
//                .password("$2a$10$90m2gguI/d0vqbPmVxTXveZH8JTmf.XndsWz1aDVS87t.IYCiJGz6") //1111
//                .role(Role.CLIENT)
//                .build();
//        user5 = User.builder()
//                .id(3L)
//                .email("user_changed@mail.com")
////                .password("$2a$10$SLGnS0kJhsBdA6Wv6LweruyZrxH1cAIs/Itkrk7dU78K8wPxg5kMi") //6666
//                .role(Role.PROVIDER)
//                .build();
//    }
//
//    @Test
//    void changeRoleSuccess(){
//        when(userRepository.findById(3L)).thenReturn(Optional.ofNullable(user3));
//        when(userRepository.save(user4)).thenReturn(user4);
//
//        userService.changeUserRole(3L, "client");
//
//        verify(userRepository, times(1)).findById(3L);
//        verify(userRepository, times(1)).save(user4);
//
//    }
//
//    @Test
//    void changeRoleUserNotFound(){
//        when(userRepository.findById(3L)).thenReturn(Optional.ofNullable(null));
//
//        assertThrows(UserNotFoundException.class, () ->userService.changeUserRole(3L, "client") );
//
//        verify(userRepository, times(1)).findById(3L);
//        verify(userRepository, times(0)).save(user4);
//    }
//
//
//}
