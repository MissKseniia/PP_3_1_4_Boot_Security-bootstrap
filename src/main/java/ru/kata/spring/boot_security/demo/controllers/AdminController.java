package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/admin/api")
@RestController
public class AdminController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final RoleDao roleDao;

    public AdminController(UserValidator userValidator, UserService userService, RoleDao roleDao) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("/users/current")
    @ResponseBody
    public User getAdmin() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @GetMapping()
    public ModelAndView adminPage(ModelAndView modelAndView) {

//        modelAndView.addObject("user", new User());
        modelAndView.setViewName("admin");

        return modelAndView;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> getAllUsers() {

        return userService.getUsers();
    }

    @PostMapping("/users")
    public ModelAndView insertUser(ModelAndView modelAndView, @Valid @RequestBody User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        modelAndView.setViewName("admin");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("user", user);

        } else {
            userService.registerUser(user);
        }

        return modelAndView;
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public User findUser(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        System.out.println("**********");
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping(value = "/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);
        return "was deleted";
    }
}
