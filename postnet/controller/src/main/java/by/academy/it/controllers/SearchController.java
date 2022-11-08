package by.academy.it.controllers;

import by.academy.it.services.SearchService;
import by.academy.it.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class SearchController {
    private final UserService userService;
    private final SearchService searchService;

    @Autowired
    public SearchController(UserService userService, SearchService searchService) {
        this.userService = userService;
        this.searchService = searchService;
    }

    @GetMapping(value = "/{page}")
    public String showUsers(@PathVariable int page, @RequestParam String searchString, Model model) {
        model.addAttribute("startpage", 1);
        model.addAttribute("endpage", Math.ceil((double) searchService.pagesCount(searchString) / 3));
        model.addAttribute("search", searchString);
        model.addAttribute("currentpage", page);
        model.addAttribute("users", searchService.pageUsers(searchString, page - 1));
        return "search/search-all";
    }

    @GetMapping("/user/{id}")
    public String showOneUser(@PathVariable("id") int id, Principal principal, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        if (principal != null && !principal.getName().equals("admin")
                && id == userService.getUserByEmail(principal.getName()).getId()) {
            return "redirect:/";
        }
        return "/search/search-one";
    }
}
