package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {

    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@Valid @ModelAttribute("user") User user, BindingResult bd) {

        userValidator.validate(user, bd);
        if (bd.hasErrors()) {
            return "registration";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }
}
