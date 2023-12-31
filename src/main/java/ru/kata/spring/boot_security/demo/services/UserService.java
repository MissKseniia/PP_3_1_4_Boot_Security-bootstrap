package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;


public interface UserService {

    void registerUser(User user);

    void deleteUserById(Long id);

    boolean userExists(User user);

    List<User> getUsers();

    User getUserById(Long id);

    void updateUser(User user);

    User save(User user);
}
