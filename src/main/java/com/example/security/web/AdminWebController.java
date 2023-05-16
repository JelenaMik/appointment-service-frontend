package com.example.security.web;

import com.example.security.repository.model.User;
import com.example.security.model.UserData;
import com.example.security.service.UserDataService;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminWebController {
    private final UserService userService;
    private final UserDataService userDataService;

    @GetMapping("/admin-page")
    public String showAdminPage(@RequestParam(required = false) String string, Model model){
        log.info("Search by email {}", string);
        if (Objects.equals(string, "")) string="";
        model.addAttribute("users", userService.findUsersBySearching(string));
        return "admin-page";
    }
    @PostMapping("/search-users")
    public String searchUsers(String emailEntered){
        return "redirect:admin-page?string="+emailEntered;
    }

    @PostMapping("/admin-change-user")
    public String showUserInfo(Long id){
        return "redirect:user-info?userId="+id;
    }

    @GetMapping("/user-info")
    public String userInfo(@RequestParam Long userId, Model model){
        User user = userService.getUserById(userId);
        UserData userData = userDataService.getUserDataByUserId(userId);
        model.addAttribute("user", user);
        model.addAttribute("userData", userData);
        return "user-info";

    }

    @PostMapping("update-password")
    public String updatePassword(Long userId){
        String newPassword = userService.adminChangePassword(userId);
        log.warn("Password was changed to {}", newPassword);
        // implement sending new password in email or message
        return "redirect:user-info?userId="+userId;
    }
}
