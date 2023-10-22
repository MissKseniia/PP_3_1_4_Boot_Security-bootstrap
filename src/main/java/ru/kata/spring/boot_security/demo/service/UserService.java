package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;


public interface UserService {

    void registerUser(User user);

    void deleteUserById(Long id);

    boolean userExists(User user);

    List<User> getUsers();

    User getUserById(Long id);

    void updateUser(User user);
}
