package by.academy.it.controllers;

import by.academy.it.services.AdminService;
import by.academy.it.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public HomeController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String profile(Principal principal, Model model) {
        if (principal.getName().equals("admin")) {
            model.addAttribute("users", adminService.getAllUsers());
            return "/administrate/admin-page";
        }
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "/registered/user-profile";
    }
}
