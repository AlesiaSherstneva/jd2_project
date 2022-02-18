package by.academy.it.controllers;

import by.academy.it.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/ban")
    public String banUser(@RequestParam("userId") int userId) {
        userDao.updateUserStatus((byte) 0, userId);
        return "redirect:/";
    }

    @GetMapping("/unban")
    public String unbanUser(@RequestParam("userId") int userId) {
        userDao.updateUserStatus((byte) 1, userId);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") int userId) {
        userDao.deleteUser(userId);
        return "redirect:/";
    }

}
