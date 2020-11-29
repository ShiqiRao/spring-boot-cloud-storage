package com.example.storage.controller;


import com.example.storage.model.TUser;
import com.example.storage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute("user") TUser user) {
        if (user == null) {
            return "redirect:signup";
        }
        try {
            userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:signup?error";
        }
        return "redirect:signup?success";
    }
}
