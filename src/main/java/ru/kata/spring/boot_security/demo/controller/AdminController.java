package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping()
    public String adminPage(@ModelAttribute("user") User user, ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        List<Role> roles = roleDao.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("admin", admin);
        model.addAttribute("allUsers", userService.getUsers());
        model.addAttribute("userEdit", new User());
        return "admin";
    }

    @PostMapping("/insert")
    public String insertUser(@Valid @ModelAttribute User user, BindingResult bd) {

        if (bd.hasErrors()) {
            return "admin";
        }
        userValidator.validate(user, bd);
        userService.registerUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/findUser")
    @ResponseBody
    public User findUser(Long id) {

        System.out.println(userService.getUserById(id));
        return userService.getUserById(id);
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {

//        if (bd.hasErrors()) {
//            return "update_user";
//        }
        System.out.println(user);
        userService.updateUser(user);

        return "redirect:/admin";
    }

    @RequestMapping(value = "/remove/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
