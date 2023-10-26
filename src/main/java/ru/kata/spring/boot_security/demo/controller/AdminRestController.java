package ru.kata.spring.boot_security.demo.controller;


import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RequestMapping("/admin/api")
@RestController
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public User findUserById(@PathVariable("id") Long id) {

       return userService.getUserById(id);

    }

    @PostMapping("/update")
    public User update(@RequestBody User user) {
        System.out.println(user);
        return userService.save(user);
    }

}
