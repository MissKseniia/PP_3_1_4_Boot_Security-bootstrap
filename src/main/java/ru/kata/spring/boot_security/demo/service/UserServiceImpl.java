package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


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
    public void registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
        userDao.save(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {

        userDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean userExists(User user) {
        Optional<User> userCheck = userDao.findByEmail(user.getUsername());
        return userCheck.isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id).get();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        User oldUser = userDao.findById(user.getId()).get();

        return userDao.save(user);
    }
}
