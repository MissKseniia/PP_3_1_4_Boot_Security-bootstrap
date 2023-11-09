package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exceptions.NotFoundException;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin/api")
@RestController
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/current")
    @ResponseBody
    public ResponseEntity<User> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>((User) authentication.getPrincipal(), HttpStatus.OK);
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<User> findUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id)
                .orElseThrow(() -> new NotFoundException("There is no user with ID " + id + " in Database")));

    }

    @GetMapping("/users/")
    @ResponseBody
    public ResponseEntity<User> findUserByEmail(@RequestParam(value = "email", required = false) String email) {

        return ResponseEntity.ok(userService.getUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("There is no user with email " + email + " in Database")));

    }

    @GetMapping("/users/emails")
    @ResponseBody
    public List<String> getAllUsersEmails() {

        return userService.getUsers().stream().map(User::getEmail).collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<User> insertUser(@RequestBody User user) {

        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);

    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);

    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {

        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
