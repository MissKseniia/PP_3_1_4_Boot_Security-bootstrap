package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final RoleDao roleDao;

    public AdminController(UserValidator userValidator, UserService userService, RoleDao roleDao) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping
    public String usersList(Model model) {
        model.addAttribute("allUsers", userService.getUsers());
        return "admin";
    }

    @GetMapping("/create")
    public String createUser(@ModelAttribute("user") User user, ModelMap model) {

        List<Role> roles = roleDao.findAll();
        model.addAttribute("roles", roles);

        return "create_user";
    }

    @PostMapping("/insert")
    public String insertUser(@Valid @ModelAttribute("user") User user, BindingResult bd) {


        userValidator.validate(user, bd);
        if (bd.hasErrors()) {
            return "create_user";
        }
        userService.registerUser(user);


        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String updateUser(@RequestParam("userId") Long userId,
                             ModelMap model) {

        List<Role> roles = roleDao.findAll();
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("roles", roles);
        System.out.println(userService.getUserById(userId));
        return "update_user";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bd,
                             @RequestParam("action") String action) {

        System.out.println(user);

        if (action.equals("Update")) {
            if (bd.hasErrors()) {
                return "update_user";
            }
            userService.updateUser(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/remove")
    public String deleteUser(@RequestParam Long userId) {

        userService.deleteUserById(userId);
        return "redirect:/admin";
    }
}
