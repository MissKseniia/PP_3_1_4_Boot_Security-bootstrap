package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exceptions.NotFoundException;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Validated
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public User registerUser(User user) {

        if (user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
        return save(user);
    }

    @Transactional
    @Override
    public Optional<User> getUserByEmail(String email) {

        return Optional.ofNullable(getUsers().stream()
                .filter(user -> user.getUsername().equals(email))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));

    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {

        userDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public User updateUser(User user) {

        return save(user);
    }

    @Override
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userDao.save(user);
    }
}
