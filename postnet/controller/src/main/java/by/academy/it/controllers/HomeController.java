package by.academy.it.controllers;

import by.academy.it.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserDao userDao;

    @Autowired
    public HomeController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String profile(Principal principal, Model model) {
        if (principal.getName().equals("admin")) {
            model.addAttribute("users", userDao.getAllUsers());
            return "/administrate/admin-page";
        }
        model.addAttribute("user", userDao.getUserByEmail(principal.getName()));
        return "/registered/user-profile";
    }
}
