package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    User registerUser(User user);

    Optional<User> getUserByEmail(String email);

    void deleteUserById(Long id);

    List<User> getUsers();

    Optional<User> getUserById(Long id);

    User updateUser(User user);

    User save(User user);
}
