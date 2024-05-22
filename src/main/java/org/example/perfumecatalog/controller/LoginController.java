package org.example.perfumecatalog.controller;

import org.example.perfumecatalog.dto.RegisterUserDto;
import org.example.perfumecatalog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute("user") RegisterUserDto user) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterUserDto user) {
        userService.registerUser(user);
        return "redirect:/perfumes";
    }
}
