package by.academy.it.controllers;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class SearchController {

    private final UserDao userDao;

    @Autowired
    public SearchController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(value = "/{page}")
    public String showUsers(@PathVariable int page, @RequestParam String searchString, Model model) {
        List<User> users = userDao.searchUsers(searchString);
        if (users.size() == 0) {
            users = userDao.getAllUsers();
        }
        model.addAttribute("startpage", 1);
        model.addAttribute("endpage", Math.ceil((double) users.size() / 3));
        model.addAttribute("search", searchString);
        model.addAttribute("currentpage", page);

        users = userDao.pageUsers(searchString, page - 1);
        model.addAttribute("users", users);
        return "search/search-all";
    }

    @GetMapping("/user/{id}")
    public String showOneUser(@PathVariable("id") int id, Principal principal, Model model) {
        model.addAttribute("user", userDao.getUser(id));
        if (principal != null && !principal.getName().equals("admin")
                && id == userDao.getUserByEmail(principal.getName()).getId()) {
            return "redirect:/";
        }
        return "/search/search-one";
    }
}
