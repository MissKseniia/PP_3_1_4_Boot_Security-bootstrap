package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

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
    public String adminPage(ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        List<Role> roles = roleDao.findAll();
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        model.addAttribute("admin", admin);
        model.addAttribute("allUsers", userService.getUsers());
        return "admin";
    }

    @PostMapping("/insert")
    public String insertUser(ModelMap model, @Valid @ModelAttribute User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.put("user", user);
            provideElementsForAdminPage(model);
            return "admin";

        } else {
            userService.registerUser(user);
        }

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

        userService.updateUser(user);
        return "redirect:/admin";
    }

    private void provideElementsForAdminPage(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        List<Role> roles = roleDao.findAll();
        model.put("roles", roles);
        model.put("admin", admin);
        model.put("allUsers", userService.getUsers());
    }

    @RequestMapping(value = "/remove/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
